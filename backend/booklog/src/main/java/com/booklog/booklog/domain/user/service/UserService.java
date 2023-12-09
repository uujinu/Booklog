package com.booklog.booklog.domain.user.service;

import com.booklog.booklog.common.util.Encryptor;
import com.booklog.booklog.domain.user.dto.UserSignUpReqDto;
import com.booklog.booklog.domain.user.repository.UserRepository;
import com.booklog.booklog.exception.DuplicateException;
import com.booklog.booklog.common.code.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final Encryptor encryptor;

    public void signUp(UserSignUpReqDto dto) {
        // 닉네임, 이메일 중복 여부 확인
        if (checkEmailDuplication(dto.getEmail())) {
            throw new DuplicateException(ErrorCode.EMAIL_DUPLICATE);
        }
        if (checkNameDuplication(dto.getName())) {
            throw new DuplicateException(ErrorCode.NAME_DUPLICATE);
        }

        // 비밀번호 암호화
        dto.setPassword(encryptor.encrypt(dto.getPassword()));

        // TODO : 프로필 이미지 처리
        userRepository.save(dto.toEntity("profile_img_path"));
    }

    // 닉네임 중복 여부 확인
    public Boolean checkNameDuplication(String name) {
        return userRepository.existsByName(name);
    }

    // 이메일 중복 여부 확인
    public Boolean checkEmailDuplication(String email) {
        return userRepository.existsByEmail(email);
    }
}