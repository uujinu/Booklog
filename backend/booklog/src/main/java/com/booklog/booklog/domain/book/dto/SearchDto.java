package com.booklog.booklog.domain.book.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDto {

    public Integer total;
    public List<Item> items;

    static class Item {
        public String title;
        public String image;
        public String author;
        public String isbn;
        public String publisher;
    }
}
