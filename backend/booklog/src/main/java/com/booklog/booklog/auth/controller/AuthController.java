package com.booklog.booklog.auth.controller;

import com.booklog.booklog.auth.dto.ReissueTokenDto;
import com.booklog.booklog.auth.dto.TokenDto;
import com.booklog.booklog.auth.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissueToken")
    public ResponseEntity<TokenDto> reissueToken(@RequestBody ReissueTokenDto dto) {
        return ResponseEntity.ok(authService.reissueAccessToken(dto));
    }
}
