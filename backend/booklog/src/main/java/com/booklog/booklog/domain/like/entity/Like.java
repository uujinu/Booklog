package com.booklog.booklog.domain.like.entity;

import com.booklog.booklog.common.domain.BaseTimeEntity;
import com.booklog.booklog.domain.post.entity.Post;
import com.booklog.booklog.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalTime;

@Table(
        name = "likes",
        uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "post_id" })
)
@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE likes SET deleted_at = current_timestamp WHERE like_id = ?")
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

    public void initDelete() {
        this.deletedAt = null;
    }

    public static Like toEntity(Post post, User user) {
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        return like;
    }
}
