package com.booklog.booklog.domain.image.entity;

import com.booklog.booklog.domain.post.entity.Post;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Setter
    private Post post;

    @NotBlank
    private String imgPath;

    @Builder
    public Image(Post post, String path) {
        this.post = post;
        this.imgPath = path;
    }

    public void setPost(Post post) {
        if (this.post != null) {
            this.post.getImages().remove(this);
        }
        this.post = post;
        post.getImages().add(this);
    }
}
