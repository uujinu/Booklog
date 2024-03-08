package com.booklog.booklog.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReissueTokenDto {

    @JsonProperty("refreshToken")
    private String refreshToken;
}
