package com.booklog.booklog.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissueTokenDto {

    private String refreshToken;
}
