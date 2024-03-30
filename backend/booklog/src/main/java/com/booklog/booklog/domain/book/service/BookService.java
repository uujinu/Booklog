package com.booklog.booklog.domain.book.service;

import com.booklog.booklog.domain.book.dto.SearchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class BookService {

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

    private HttpEntity<String> getHttpEntity() { // 헤더에 인증 정보 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", id);
        headers.set("X-Naver-Client-Secret", secret);
        return new HttpEntity<>(headers);
    }
}
