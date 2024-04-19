package com.booklog.booklog.domain.like.service;

import com.booklog.booklog.auth.Auth;
import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.domain.like.entity.Like;
import com.booklog.booklog.domain.like.repository.LikeRepository;
import com.booklog.booklog.domain.post.entity.Post;
import com.booklog.booklog.domain.post.repository.PostRepository;
import com.booklog.booklog.domain.user.entity.User;
import com.booklog.booklog.domain.user.repository.UserRepository;
import com.booklog.booklog.exception.NoSuchDataException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    @Auth
    public String clickLike(Long userId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchDataException(ErrorCode.NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchDataException(ErrorCode.USER_NOT_FOUND));

        Optional<Like> like = likeRepository.findByUserIdAndPostId(user.getId(), post.getId());

        // 좋아요가 이미 존재하는 경우
        if (like.isPresent()) {
            if (like.get().getDeletedAt() == null) { // 삭제되지 않은 상태
                likeRepository.delete(like.get());
                return "좋아요를 취소했습니다.";
            } else { // 삭제된 상태
                like.get().initDelete(); // 좋아요 복구
                return "좋아요를 눌렀습니다.";
            }
        }

        // 처음 좋아요를 하는 경우
        likeRepository.save(Like.toEntity(post, user));
        return "좋아요를 눌렀습니다.";
    }

    @Transactional(readOnly = true)
    public Integer getPostLikeCount(Long postId) {
        Post post = postRepository.findById(Long.valueOf(postId))
                .orElseThrow(() -> new NoSuchDataException(ErrorCode.POST_NOT_FOUND));
        return likeRepository.countByPost(post);
    }

    @Transactional(readOnly = true)
    public Integer getUserLikeCount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchDataException(ErrorCode.USER_NOT_FOUND));
        return likeRepository.countByUser(user);
    }

    @Transactional(readOnly = true)
    public Boolean checkLike(Long userId, Long postId) {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId).orElse(null);
        return like != null && like.getDeletedAt() == null;
    }
}
