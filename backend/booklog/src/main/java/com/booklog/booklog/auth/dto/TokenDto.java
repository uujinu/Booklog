package com.booklog.booklog.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {

    private String userId;
    private String accessToken;
    private String refreshToken;
}
