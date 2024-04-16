package com.booklog.booklog.domain.user.entity;

import com.booklog.booklog.common.domain.BaseTimeEntity;
import com.booklog.booklog.domain.book.entity.Book;
import com.booklog.booklog.domain.comment.entity.Comment;
import com.booklog.booklog.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    private static String INTRODUCTION_DEFAULT = "Booklog와 함께 즐거운 독서해요!";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Column(length = 50, unique = true)
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Column(unique = true)
    private String email;

    @Nullable
    private String profileImgUrl;

    @NotNull
    private LocalDate birthday;

    @Column(length = 200)
    @Builder.Default
    private String introduction = INTRODUCTION_DEFAULT;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void addBook(Book book) {
        if (!getBooks().contains(book)) {
            getBooks().add(book);
        }
        book.setUser(this);
    }

    public void addPost(Post post) {
        if (!getPosts().contains(post)) {
            getPosts().add(post);
        }
        post.setUser(this);
    }

    public void addComment(Comment comment) {
        if (!getComments().contains(comment)) {
            getComments().add(comment);
        }
        comment.setUser(this);
    }

    public void setName(String name) { this.name = name; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileImgUrl(String url) { this.profileImgUrl = url; }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}