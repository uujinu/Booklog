package com.booklog.booklog.domain.comment.repository;

import com.booklog.booklog.domain.comment.entity.Comment;
import com.booklog.booklog.domain.post.entity.Post;
import com.booklog.booklog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.deletedAt is null and c.post = :post")
    Integer countByPost(@Param("post") Post post);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.deletedAt is null and c.user = :user")
    Integer countByUser(@Param("user") User user);
}
