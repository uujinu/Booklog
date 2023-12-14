package com.booklog.booklog.domain.user.service;

import com.booklog.booklog.common.util.Encryptor;
import com.booklog.booklog.domain.user.dto.UserSignUpReqDto;
import com.booklog.booklog.domain.user.entity.User;
import com.booklog.booklog.domain.user.repository.UserRepository;
import com.booklog.booklog.exception.DuplicateException;
import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.infra.email.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final Encryptor encryptor;

    // 회원 가입
    public void signUp(UserSignUpReqDto dto) {
        // 이메일, 이름 중복 여부 재확인
        checkEmailDuplication(dto.getEmail());
        checkNameDuplication(dto.getName());

        // 비밀번호 암호화
        dto.setPassword(encryptor.encrypt(dto.getPassword()));

        // TODO : 프로필 이미지 처리
        userRepository.save(dto.toEntity("profile_img_path"));
    }

    // 닉네임 중복 여부 확인
    public void checkNameDuplication(String name) {
        if (userRepository.existsByName(name)) {
            throw new DuplicateException(ErrorCode.NAME_DUPLICATE);
        }
    }

    // 이메일 중복 여부 확인
    public void checkEmailDuplication(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new DuplicateException(ErrorCode.EMAIL_DUPLICATE);
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) { return userRepository.findByEmail(email); }

    @Transactional(readOnly = true)
    public Optional<User> findUserByName(String name) { return userRepository.findByName(name); }
}