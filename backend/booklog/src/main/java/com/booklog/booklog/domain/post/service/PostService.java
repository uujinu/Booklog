package com.booklog.booklog.domain.post.service;

import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.domain.image.entity.Image;
import com.booklog.booklog.domain.image.repository.ImageRepository;
import com.booklog.booklog.domain.image.service.ImageService;
import com.booklog.booklog.domain.like.repository.LikeRepository;
import com.booklog.booklog.domain.like.service.LikeService;
import com.booklog.booklog.domain.post.dto.PostListDto;
import com.booklog.booklog.domain.post.dto.PostReqDto;
import com.booklog.booklog.domain.post.dto.PostResDto;
import com.booklog.booklog.domain.post.dto.PostUpdateDto;
import com.booklog.booklog.domain.post.entity.Post;
import com.booklog.booklog.domain.post.repository.PostRepository;
import com.booklog.booklog.domain.user.entity.User;
import com.booklog.booklog.domain.user.repository.UserRepository;
import com.booklog.booklog.exception.NoSuchDataException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ImageRepository imageRepository;
    private final LikeService likeService;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    public List<PostListDto> getAllPublicPost() {
        return postRepository.findPublicPosts();
    }

    @Transactional(readOnly = true)
    public List<PostListDto> getAllPost(Long userId) {
        return postRepository.findUserPosts(userId);
    }

    @Transactional(readOnly = true)
    public PostResDto getPost(Long id, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException(ErrorCode.POST_NOT_FOUND));

        int likeNum = likeRepository.countByPost(post);
        boolean likeCheck = userId != null && likeService.checkLike(userId, post.getId());
        return PostResDto.of(post, likeNum, likeCheck);
    }

    @Transactional
    public Long create(Long userId, List<MultipartFile> files, PostReqDto dto) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchDataException(ErrorCode.USER_NOT_FOUND));
        Post post = dto.toEntity(user);
        postRepository.save(post);
        addImages(post, files);

        return post.getId();
    }

    @Transactional
    public void update(Long id, PostUpdateDto dto, List<MultipartFile> files) throws IOException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException(ErrorCode.POST_NOT_FOUND));

        // post 수정
        dto.updatePost(post);

        // 이미지 삭제
        List<String> urls = dto.getUrls();
        for (String url : urls) {
            Image image = imageRepository.findByImgPath(url).orElse(null);
            if (image != null) {
                imageService.delete(url.substring(50));
                post.getImages().remove(image);
            }
        }

        // 이미지 추가
        addImages(post, files);
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException(ErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }

    @Transactional
    private void addImages(Post post, List<MultipartFile> files) throws IOException {
        List<String> urls = imageService.uploadPostImage("Post", post.getId(), files);
        for (String url : urls) {
            if (!url.isBlank()) {
                post.addImage(new Image(post, url));
            }
        }
    }
}
