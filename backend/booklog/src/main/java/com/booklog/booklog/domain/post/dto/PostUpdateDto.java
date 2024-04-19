package com.booklog.booklog.domain.post.dto;

import com.booklog.booklog.domain.post.entity.Post;
import com.booklog.booklog.domain.post.entity.PostBook;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class PostUpdateDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "문장을 입력해주세요.")
    private String sentence;

    @NotNull
    private PostBook postBook;
    private String content;
    private List<String> urls;
    private boolean isPublic;

    public PostUpdateDto(String title, String sentence, PostBook postBook, String content, List<String> urls, boolean isPublic) {
        this.title = title;
        this.sentence = sentence;
        this.postBook = postBook;
        this.content = content;
        this.urls = urls;
        this.isPublic = isPublic;
    }

    public void updatePost(Post post) {
        post.update(title, sentence, postBook, content, isPublic);
    }
}
