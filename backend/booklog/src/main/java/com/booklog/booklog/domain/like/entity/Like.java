package com.booklog.booklog.domain.like.entity;

import com.booklog.booklog.common.domain.BaseTimeEntity;
import com.booklog.booklog.domain.post.entity.Post;
import com.booklog.booklog.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalTime;

@Table(name = "likes")
@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    private LocalTime deletedAt;

    public void initLike(Like like) {
        this.deletedAt = null;
    }

    public static Like toEntity(Post post, User user) {
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        return like;
    }
}
