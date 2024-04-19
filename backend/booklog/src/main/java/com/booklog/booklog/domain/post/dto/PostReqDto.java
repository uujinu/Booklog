package com.booklog.booklog.domain.post.dto;

import com.booklog.booklog.domain.post.entity.Post;
import com.booklog.booklog.domain.post.entity.PostBook;
import com.booklog.booklog.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostReqDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "문장을 입력해주세요.")
    private String sentence;

    @NotNull
    private PostBook postBook;
    private String content;
    private boolean isPublic;

    @Builder
    public PostReqDto(String title, String sentence, PostBook postBook, String content, boolean isPublic) {
        this.title = title;
        this.sentence = sentence;
        this.postBook = postBook;
        this.content = content;
        this.isPublic = isPublic;
    }

    public Post toEntity(User user) {
        return Post.postBuilder()
                .title(title)
                .user(user)
                .sentence(sentence)
                .postBook(postBook)
                .content(content)
                .isPublic(isPublic)
                .build();
    }
}
