package com.booklog.booklog.auth.domain;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    @Value("${jwt.expire.access}")
    private long accessTokenValidTime;

    @Value("${jwt.expire.refresh}")
    private long refreshTokenValidTime;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createAccessToken(String userId) {
        return createToken(userId, TokenType.ACCESS, accessTokenValidTime);
    }

    public String createRefreshToken(String userId) {
        return createToken(userId, TokenType.REFRESH, refreshTokenValidTime);
    }

    public String createToken(String userId, TokenType tokenType, long tokenValidTime) {

        long now = (new Date()).getTime();
        Date expirationDate = new Date(now + 1000 * 60 * tokenValidTime);

        Map<String, String> tokenInfo = new HashMap<>();
        tokenInfo.put("tokenType", tokenType.name());

        return Jwts.builder()
                .subject(userId)
                .claims(tokenInfo)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // 토큰으로부터 회원 정보 조회
    public String getUserId(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    // http 헤더로부터 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private enum TokenType {
        REFRESH, ACCESS
    }
}
