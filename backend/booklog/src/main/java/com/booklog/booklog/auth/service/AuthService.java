package com.booklog.booklog.auth.service;

import com.booklog.booklog.auth.domain.JwtTokenProvider;
import com.booklog.booklog.auth.dto.ReissueTokenDto;
import com.booklog.booklog.auth.dto.TokenDto;
import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.common.util.RedisUtil;
import com.booklog.booklog.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @Value("${jwt.expire.refresh}")
    private long refreshTokenValidTime;

    // accessToken 토큰 갱신
    public TokenDto reissueAccessToken(ReissueTokenDto dto) {

        String refreshToken = dto.getRefreshToken();

        // refreshToken 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthorizationException(ErrorCode.EXPIRED_TOKEN);
        }

        // userId 가져오기
        String userId = jwtTokenProvider.getUserId(refreshToken);

        // redis에서 refreshToken 가져오기
        String token = getRefreshToken(userId);
        if (!token.equals(refreshToken)) {
            throw new AuthorizationException(ErrorCode.NOT_EXIST_TOKEN);
        }

        // 토큰 재발행
        TokenDto tokenDto = TokenDto.builder()
                .userId(userId)
                .accessToken(jwtTokenProvider.createAccessToken(userId))
                .refreshToken(jwtTokenProvider.createRefreshToken(userId))
                .build();

        saveRefreshToken(tokenDto);

        return tokenDto;
    }

    // redis에서 refreshToken 찾기
    public String getRefreshToken(String userId) {

        String refreshToken = redisUtil.getData(userId);
        if (refreshToken == null) {
            throw new AuthorizationException(ErrorCode.NOT_EXIST_TOKEN);
        }

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthorizationException(ErrorCode.EXPIRED_TOKEN);
        }

        return refreshToken;
    }

    // redis에 refreshToken 저장
    public void saveRefreshToken(TokenDto dto) {
        String token = redisUtil.getData(dto.getUserId());
        redisUtil.setDataExpire(dto.getUserId(), dto.getRefreshToken(), refreshTokenValidTime);
    }
}
