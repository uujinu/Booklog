package com.booklog.booklog.domain.post.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListDto {

    private Long id;
    private String user;
    private String title;
    private Long likeNum;
    private Long commentNum;
    private String createdAt;

    public PostListDto(Long id, String user, String title, LocalDateTime createdAt, Long likeNum, Long commentNum) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }
}
