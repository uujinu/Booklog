package com.booklog.booklog.domain.book.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDetailResDto {

    public String title;
    public String thumbnail;
    public String author;
    public String isbn;
    public String publisher;
    public String description;
    public String pubDate;
    public Integer totalPage;
}
