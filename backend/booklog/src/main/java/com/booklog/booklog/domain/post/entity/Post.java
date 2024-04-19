package com.booklog.booklog.domain.post.entity;

import com.booklog.booklog.common.domain.BaseTimeEntity;
import com.booklog.booklog.domain.comment.entity.Comment;
import com.booklog.booklog.domain.image.entity.Image;
import com.booklog.booklog.domain.like.entity.Like;
import com.booklog.booklog.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "title", column = @Column(name = "pb_title")),
            @AttributeOverride(name = "image", column = @Column(name = "pb_image")),
            @AttributeOverride(name = "author", column = @Column(name = "pb_author")),
            @AttributeOverride(name = "publisher", column = @Column(name = "pb_publisher")),
            @AttributeOverride(name = "isbn", column = @Column(name = "pb_isbn"))
    })
    private PostBook postBook;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void addImage(Image image) {
        if (!getImages().contains(image)) {
            getImages().add(image);
        }
        image.setPost(this);
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
}
