package com.booklog.booklog.domain.post.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder(builderClassName = "postBookBuilder")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostBook {

    private String title;
    private String image;
    private String author;
    private String publisher;
    private String isbn;
}
