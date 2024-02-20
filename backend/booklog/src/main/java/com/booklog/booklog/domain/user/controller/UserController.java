package com.booklog.booklog.domain.user.controller;

import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.common.response.ResponseDto;
import com.booklog.booklog.domain.user.dto.EmailPWReqDto;
import com.booklog.booklog.domain.user.dto.LoginReqDto;
import com.booklog.booklog.auth.dto.TokenDto;
import com.booklog.booklog.domain.user.dto.UserSignUpReqDto;
import com.booklog.booklog.domain.user.entity.User;
import com.booklog.booklog.domain.user.service.UserService;
import com.booklog.booklog.exception.EmailException;
import com.booklog.booklog.exception.NoSuchDataException;
import com.booklog.booklog.infra.email.EmailCodeDto;
import com.booklog.booklog.infra.email.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signUp(@Valid @RequestBody UserSignUpReqDto dto) throws Exception {
        userService.signUp(dto);
        return ResponseEntity.ok(ResponseDto.of("회원가입이 완료되었습니다."));
    }

    // 회원가입 이메일 인증 요청
    @GetMapping("/signup/email")
    public ResponseEntity<ResponseDto<String>> sendEmail(@RequestParam("email") String email) throws Exception {
        // 이메일 중복 체크
        userService.checkEmailDuplication(email);

        emailService.sendCodeToEmail(email);
        return ResponseEntity.ok(ResponseDto.of("인증 이메일을 확인해주세요."));
    }

    // 회원가입 이메일 인증 확인
    @PostMapping("/signup/email-verification")
    public ResponseEntity<ResponseDto<String>> verifyEmail(@Valid @RequestBody EmailCodeDto dto) {
        if (!emailService.verifyEmailCode(dto)) {
            throw new EmailException(ErrorCode.VERIFICATION_FAILED);
        }
        return ResponseEntity.ok(ResponseDto.of("이메일 인증이 완료되었습니다."));
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<TokenDto>> login(@Valid @RequestBody LoginReqDto dto) throws Exception {
        return ResponseEntity.ok(ResponseDto.of(userService.signIn(dto)));
    }

    // 이메일 중복 체크
    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseDto<Boolean>> checkEmailDuplicate(@PathVariable String email) {
        userService.checkEmailDuplication(email);
        return ResponseEntity.ok(ResponseDto.ofSuccess());
    }

    // 닉네임 중복 체크
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseDto<Boolean>> checkNameDuplicate(@PathVariable String name) {
        userService.checkNameDuplication(name);
        return ResponseEntity.ok(ResponseDto.ofSuccess());
    }

    // 비밀번호 재설정 - 인증 이메일 전송
    @GetMapping("/password")
    public ResponseEntity<ResponseDto<String>> sendEmailToChangePW(@RequestParam("email") String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new NoSuchDataException(ErrorCode.USER_NOT_FOUND);
        }

        emailService.sendCodeToEmail(email);
        return ResponseEntity.ok(ResponseDto.of("인증 이메일을 확인해주세요."));
    }

    // 비밀번호 재설정 - 인증 코드 확인
    @PostMapping("/password")
    public ResponseEntity<ResponseDto<String>> checkEmailToChangePW(@Valid @RequestBody EmailCodeDto dto) {
        if (!emailService.verifyEmailCode(dto)) {
            throw new EmailException(ErrorCode.VERIFICATION_FAILED);
        }
        return ResponseEntity.ok(ResponseDto.of("이메일 인증이 완료되었습니다."));
    }

    // 비밀번호 재설정 - 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<ResponseDto<String>> updatePassword(@Valid @RequestBody EmailPWReqDto dto) {
        userService.updatePassword(dto);
        return ResponseEntity.ok(ResponseDto.of("비밀번호가 변경되었습니다."));
    }

    // 회원 탈퇴
    @DeleteMapping("")
    public ResponseEntity<ResponseDto<String>> deleteUser(@Valid @RequestBody EmailPWReqDto dto) {
        userService.deleteUser(dto);
        return ResponseEntity.ok(ResponseDto.of("회원 탈퇴가 완료되었습니다."));
    }
}