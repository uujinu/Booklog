package com.booklog.booklog.domain.like.repository;

import com.booklog.booklog.domain.like.entity.Like;
import com.booklog.booklog.domain.post.entity.Post;
import com.booklog.booklog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.deletedAt is null and l.post = :post")
    Integer countByPost(@Param("post") Post post);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.deletedAt is null and l.user = :user")
    Integer countByUser(@Param("user") User user);
}
