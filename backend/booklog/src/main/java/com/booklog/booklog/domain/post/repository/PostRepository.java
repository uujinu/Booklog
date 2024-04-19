package com.booklog.booklog.domain.post.repository;

import com.booklog.booklog.domain.post.dto.PostListDto;
import com.booklog.booklog.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);

    @Query(value = "SELECT new com.booklog.booklog.domain.post.dto.PostListDto" +
                    "(p.id id, u.name user, p.title title, p.createdAt createdAt, " +
                    "(SELECT COUNT(l) likeNum FROM Like l WHERE l.post.id = p.id), " +
                    "(SELECT COUNT(c) countNum FROM Comment c WHERE c.post.id = p.id)) " +
                    " FROM Post p JOIN p.user u " +
                    " WHERE p.isPublic = true ")
    List<PostListDto> findPublicPosts();

    @Query(value = "SELECT new com.booklog.booklog.domain.post.dto.PostListDto " +
                    "(p.id id, u.name user, p.title title, p.createdAt createdAt, " +
                    "(SELECT COUNT(l) likeNum FROM Like l WHERE l.post.id = p.id), " +
                    "(SELECT COUNT(c) countNum FROM Comment c WHERE c.post.id = p.id)) " +
                    "FROM Post p JOIN p.user u " +
                    "WHERE u.id = :userId "
    )
    List<PostListDto> findUserPosts(@Param("userId") Long userId);
}
