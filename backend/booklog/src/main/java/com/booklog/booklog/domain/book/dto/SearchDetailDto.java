package com.booklog.booklog.domain.book.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDetailDto {

    public List<Item> items;

    static public class Item {
        public String title;
        public String image;
        public String author;
        public String isbn;
        public String publisher;
        public String description;
        public String pubdate;
    }
}
