package com.booklog.booklog.domain.user.service;

import com.booklog.booklog.auth.service.AuthService;
import com.booklog.booklog.common.util.Encryptor;
import com.booklog.booklog.auth.domain.JwtTokenProvider;
import com.booklog.booklog.domain.user.dto.*;
import com.booklog.booklog.auth.dto.TokenDto;
import com.booklog.booklog.domain.user.entity.User;
import com.booklog.booklog.domain.user.repository.UserRepository;
import com.booklog.booklog.exception.AuthorizationException;
import com.booklog.booklog.exception.DuplicateException;
import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.exception.NoSuchDataException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final Encryptor encryptor;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    // 회원 가입
    @Transactional
    public void signUp(UserSignUpReqDto dto) {
        // 이메일, 이름 중복 여부 재확인
        checkEmailDuplication(dto.getEmail());
        checkNameDuplication(dto.getName());

        // 비밀번호 암호화
        dto.setPassword(encryptor.encrypt(dto.getPassword()));

        // TODO : 프로필 이미지 처리
        userRepository.save(dto.toEntity("profile_img_path"));
    }

    // 로그인
    @Transactional
    public LoginResDto signIn(LoginReqDto dto) {
        User user = findUserByEmail(dto.getEmail());
        if (user == null) {
            throw new AuthorizationException(ErrorCode.LOGIN_FAILED);
        }

        if (!encryptor.isMatch(dto.getPassword(), user.getPassword())) {
            throw new AuthorizationException(ErrorCode.LOGIN_FAILED);
        }

        String userId = String.valueOf(user.getId());
        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        TokenDto tokenDto = TokenDto.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        UserDto userDto = getUserInfo(user, true);

        LoginResDto loginResDto = LoginResDto.builder()
                .tokenDto(tokenDto)
                .userDto(userDto)
                .build();

        // refreshToken redis에 저장
        authService.saveRefreshToken(tokenDto);

        return loginResDto;
    }

    // 닉네임 중복 여부 확인
    public void checkNameDuplication(String name) {
        if (userRepository.existsByName(name)) {
            throw new DuplicateException(ErrorCode.NAME_DUPLICATE);
        }
    }

    // 이메일 중복 여부 확인
    public void checkEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateException(ErrorCode.EMAIL_DUPLICATE);
        }
    }

    // 비밀번호 재설정
    @Transactional
    public void updatePassword(EmailPWReqDto dto) {
        User user = findUserByEmail(dto.getEmail());
        if (user == null) {
            throw new NoSuchDataException(ErrorCode.USER_NOT_FOUND);
        }

        user.setPassword(encryptor.encrypt(dto.getPassword()));
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(EmailPWReqDto dto) {
        User user = findUserByEmail(dto.getEmail());
        if (user == null) {
            throw new NoSuchDataException(ErrorCode.USER_NOT_FOUND);
        }

        userRepository.delete(user);
    }

    // 회원 정보
    public UserDto getInfo(String id, boolean isPrivate) {
        User user = findUserById(Long.valueOf(id));
        if (user == null) {
            throw new NoSuchDataException(ErrorCode.USER_NOT_FOUND);
        }

        return getUserInfo(user, isPrivate);
    }

    private UserDto getUserInfo(User user, boolean isPrivate) {
        UserDto dto = UserDto.builder()
                .id(String.valueOf(user.getId()))
                .name(user.getName())
                .profileImgUrl(user.getProfileImgUrl())
                .introduction(user.getIntroduction())
                .build();

        // 본인 요청일 경우
        if (isPrivate) {
            dto.setEmail(user.getEmail());
            dto.setBirthday(user.getBirthday());
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) { return userRepository.findByEmail(email).orElse(null); }

    @Transactional(readOnly = true)
    public User findUserByName(String name) { return userRepository.findByName(name).orElse(null); }

    @Transactional(readOnly = true)
    public User findUserById(Long id) { return userRepository.findById(id).orElse(null); }
}