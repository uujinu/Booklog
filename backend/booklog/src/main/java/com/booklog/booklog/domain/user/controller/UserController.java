package com.booklog.booklog.domain.user.controller;

import com.booklog.booklog.common.response.ResponseDto;
import com.booklog.booklog.domain.user.dto.UserSignUpReqDto;
import com.booklog.booklog.domain.user.service.UserService;
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

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Boolean>> signUp(@Valid @RequestBody UserSignUpReqDto dto) {
        userService.signUp(dto);
        return ResponseEntity.ok(ResponseDto.ofSuccess());
    }

    // 이메일, 닉네임 중복체크
    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseDto<Boolean>> checkEmailDuplicate(@PathVariable String email) {
        Boolean checkEmail = userService.checkEmailDuplication(email);
        return ResponseEntity.ok(ResponseDto.of(checkEmail));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseDto<Boolean>> checkNameDuplicate(@PathVariable String name) {
        Boolean checkName = userService.checkNameDuplication(name);
        return ResponseEntity.ok(ResponseDto.of(checkName));
    }
}