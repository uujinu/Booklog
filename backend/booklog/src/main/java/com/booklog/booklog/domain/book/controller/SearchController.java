package com.booklog.booklog.domain.book.controller;

import com.booklog.booklog.common.response.ResponseDto;
import com.booklog.booklog.domain.book.dto.SearchDetailResDto;
import com.booklog.booklog.domain.book.dto.SearchDto;
import com.booklog.booklog.domain.book.service.SearchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/books")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<SearchDto>> search(@RequestParam("query") String query, @RequestParam("start") String start) {
        return ResponseEntity.ok(ResponseDto.of(searchService.searchBook(query, start)));
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseDto<SearchDetailResDto>> search(@RequestParam("isbn") String isbn) {
        return ResponseEntity.ok(ResponseDto.of(searchService.searchBookDetail(isbn)));
    }
}
