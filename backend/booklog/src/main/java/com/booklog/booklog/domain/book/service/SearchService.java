package com.booklog.booklog.domain.book.service;

import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.domain.book.dto.SearchDetailDto;
import com.booklog.booklog.domain.book.dto.SearchDetailResDto;
import com.booklog.booklog.domain.book.dto.SearchDto;
import com.booklog.booklog.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SearchService {

    @Value("${naver.id}")
    private String id;

    @Value("${naver.secret}")
    private String secret;

    @Value("${aladin.url}")
    private String pageUrl;

    private final String SEARCH_URL = "https://openapi.naver.com/v1/search/book.json?display=20";
    private final String DETAIL_URL = "https://openapi.naver.com/v1/search/book_adv.json";

    // 책 검색
    public SearchDto searchBook(String query, String start) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = getHttpEntity();

        URI uri = UriComponentsBuilder
                .fromUriString(SEARCH_URL)
                .queryParam("query", query)
                .queryParam("start", start)
                .encode().build().toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, SearchDto.class).getBody();
    }

    // 책 상세 검색
    public SearchDetailResDto searchBookDetail(String isbn) {
        if (!Pattern.matches("^.{13}$", isbn)) {
            throw new RestApiException(ErrorCode.BAD_REQUEST);
        }

        List<SearchDetailDto.Item> result = searchBookInfo(isbn).getItems();

        if (result.isEmpty()) {
            throw new RestApiException(ErrorCode.BAD_REQUEST);
        }

        SearchDetailDto.Item item = result.get(0);
        Integer page = searchPage(isbn);

        return SearchDetailResDto.builder()
                .title(item.title)
                .author(item.author)
                .thumbnail(item.image)
                .publisher(item.publisher)
                .description(item.description)
                .isbn(item.isbn)
                .pubDate(item.pubdate)
                .totalPage(page)
                .build();
    }

    private SearchDetailDto searchBookInfo(String isbn) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = getHttpEntity();

        URI uri = UriComponentsBuilder
                .fromUriString(DETAIL_URL)
                .queryParam("d_isbn", isbn)
                .encode().build().toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, SearchDetailDto.class).getBody();
    }

    private Integer searchPage(String isbn) {
        RestTemplate restTemplate = new RestTemplate();
        Map obj = restTemplate.getForObject(pageUrl, Map.class, isbn);
        return (Integer) ((Map) ((Map) ((List) obj.get("item")).get(0)).get("subInfo")).get("itemPage");
    }

    private HttpEntity<String> getHttpEntity() { // 헤더에 네이버 인증 정보 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", id);
        headers.set("X-Naver-Client-Secret", secret);
        return new HttpEntity<>(headers);
    }
}
