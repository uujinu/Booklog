package com.booklog.booklog.domain.post.dto;

import com.booklog.booklog.domain.image.entity.Image;
import com.booklog.booklog.domain.post.entity.Post;
import com.booklog.booklog.domain.post.entity.PostBook;
import com.booklog.booklog.domain.user.dto.UserDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResDto {

    private Long id;
    private String title;
    private String sentence;
    private PostBook postBook;
    private UserDto user;
    private String content;
    private List<String> urls;
    private int likeNum;
    private Boolean likeCheck;

    public static PostResDto of(Post post, int likeNum, Boolean likeCheck) {

        return PostResDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .sentence(post.getSentence())
                .postBook(post.getPostBook())
                .user(UserDto.of(post.getUser()))
                .content(post.getContent())
                .urls(post.getImages().stream().map(Image::getImgPath).collect(Collectors.toList()))
                .likeNum(likeNum)
                .likeCheck(likeCheck)
                .build();
    }
}
