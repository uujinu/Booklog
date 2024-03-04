package com.booklog.booklog.domain.user.dto;

import com.booklog.booklog.auth.dto.TokenDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class LoginResDto {

    @JsonProperty("TOKEN")
    TokenDto tokenDto;
    @JsonProperty("USER")
    UserDto userDto;
}
