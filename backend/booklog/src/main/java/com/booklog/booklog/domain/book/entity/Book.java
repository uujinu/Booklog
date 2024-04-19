package com.booklog.booklog.domain.book.entity;

import com.booklog.booklog.domain.user.entity.User;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @Setter
    private User user;

    @NotBlank
    private String title;

    @Column(length = 300)
    private String thumbnail;

    private String author;

    private String publisher;

    @Lob
    private String description;

    @NotBlank
    private String isbn;

    private Integer totalPage;

    @Column(length = 300)
    @NotBlank
    private String url;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer score;

    private Integer readPage;

    @Builder
    public Book(User user, String title, String thumbnail, String author, String publisher, String description,
                String isbn, Integer totalPage, String url, BookStatus bookStatus, LocalDate startDate, LocalDate endDate,
                Integer score, Integer readPage) {
        Assert.notNull(user, "User must not be null");
        Assert.notNull(title, "Title must not be null");
        Assert.notNull(isbn, "Isbn must not be null");
        Assert.notNull(url, "Url must not be null");

        this.user = user;
        this.title = title;
        this.thumbnail = thumbnail;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.isbn = isbn;
        this.totalPage = totalPage;
        this.url = url;
        this.bookStatus = bookStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.score = score;
        this.readPage = readPage;
        if (!user.getBooks().contains(this)) {
            user.getBooks().add(this);
        }
    }

    public void updateBookStatus(BookStatus bookStatus, LocalDate startDate, LocalDate endDate, Integer readPage,
                                 Integer score) {
        this.bookStatus = bookStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.readPage = readPage;
        this.score = score;
    }

    public void updateBookInfo(String title, String author, String publisher, Integer totalPage, String thumbnail) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.totalPage = totalPage;
        this.thumbnail = thumbnail;
    }
}
