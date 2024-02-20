package com.booklog.booklog.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReissueTokenDto {

    private String refreshToken;
}
