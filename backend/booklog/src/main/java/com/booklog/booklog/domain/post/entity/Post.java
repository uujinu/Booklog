package com.booklog.booklog.domain.post.entity;

import com.booklog.booklog.common.domain.BaseTimeEntity;
import com.booklog.booklog.domain.comment.entity.Comment;
import com.booklog.booklog.domain.image.entity.Image;
import com.booklog.booklog.domain.like.entity.Like;
import com.booklog.booklog.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @NotNull
    private String title;

    @NotBlank
    @Column(length = 300)
    private String sentence;

    @OneToOne(targetEntity = Post.PostBook.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Post.PostBook postBook;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Like> likes  = new ArrayList<>();

    @Lob
    private String content;

    @ColumnDefault("true")
    private Boolean isPublic;

    @Builder(builderMethodName = "postBuilder")
    public Post(User user, String title, String sentence, PostBook postBook, String content, Boolean isPublic) {
        this.user = user;
        this.title = title;
        this.sentence = sentence;
        this.postBook = postBook;
        this.content = content;
        this.isPublic = isPublic;
    }

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public void addComment(Comment comment) {
        if (!getComments().contains(comment)) {
            getComments().add(comment);
        }
        comment.setPost(this);
    }

    public void update(String title, String sentence, PostBook postBook, String content, Boolean isPublic) {
        this.title = title;
        this.sentence = sentence;
        this.postBook = postBook;
        this.content = content;
        this.isPublic = isPublic;
    }

    @Builder
    @Entity
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostBook {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title;
        private String image;
        private String author;
        private String publisher;
        private String isbn;
    }
}
