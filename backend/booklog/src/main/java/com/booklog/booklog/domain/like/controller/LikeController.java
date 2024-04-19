package com.booklog.booklog.domain.like.controller;

import com.booklog.booklog.auth.Auth;
import com.booklog.booklog.common.response.ResponseDto;
import com.booklog.booklog.domain.like.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}/likes")
    @Auth
    public ResponseEntity<ResponseDto> likePost(@PathVariable Long postId, HttpServletRequest request) {
        Long userId = Long.parseLong((String) request.getAttribute("userId"));
        return ResponseEntity.ok(ResponseDto.of(likeService.clickLike(userId, postId)));
    }

    @GetMapping("/{postId}/likes/check")
    @Auth
    public ResponseEntity<ResponseDto> checkLike(@PathVariable Long postId, HttpServletRequest request) {
        Long userId = Long.parseLong((String) request.getAttribute("userId"));
        return ResponseEntity.ok(ResponseDto.of(likeService.checkLike(userId, postId)));
    }
}
