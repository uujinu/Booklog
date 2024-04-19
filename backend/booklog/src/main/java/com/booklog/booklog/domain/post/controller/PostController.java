package com.booklog.booklog.domain.post.controller;

import com.booklog.booklog.auth.Auth;
import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.common.response.ResponseDto;
import com.booklog.booklog.domain.post.dto.PostListDto;
import com.booklog.booklog.domain.post.dto.PostReqDto;
import com.booklog.booklog.domain.post.dto.PostResDto;
import com.booklog.booklog.domain.post.dto.PostUpdateDto;
import com.booklog.booklog.domain.post.service.PostService;
import com.booklog.booklog.exception.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<List<PostListDto>>> getAllPostPublicTrue() {
        return ResponseEntity.ok(ResponseDto.of(postService.getAllPublicPost()));
    }

    @GetMapping("/my")
    @Auth
    public ResponseEntity<ResponseDto<List<PostListDto>>> getAllPost(HttpServletRequest request) {
        Long id = Long.parseLong((String) request.getAttribute("userId"));
        return ResponseEntity.ok(ResponseDto.of(postService.getAllPost(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PostResDto>> getPost(@PathVariable Long id, @RequestParam(required = false) Long userId) {
        if (id == null) {
            throw new RestApiException(ErrorCode.BAD_REQUEST);
        }

        return ResponseEntity.ok(ResponseDto.of(postService.getPost(id, userId)));
    }

    @PostMapping("/new")
    @Auth
    public ResponseEntity<ResponseDto<Boolean>> enroll(
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestPart(value = "post") @Valid PostReqDto dto,
            HttpServletRequest request) throws IOException {
        Long id = Long.parseLong((String) request.getAttribute("userId"));
        postService.create(id, files, dto);
        return ResponseEntity.ok(ResponseDto.ofSuccess());
    }

    @PatchMapping("/{id}/update")
    @Auth
    public ResponseEntity<ResponseDto<Boolean>> update(
            @PathVariable Long id,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestPart(value = "post") PostUpdateDto dto) throws IOException {

        postService.update(id, dto, files);
        return ResponseEntity.ok(ResponseDto.ofSuccess());
    }

    @DeleteMapping("/{id}/delete")
    @Auth
    public ResponseEntity<ResponseDto<Boolean>> update(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok(ResponseDto.ofSuccess());
    }
}
