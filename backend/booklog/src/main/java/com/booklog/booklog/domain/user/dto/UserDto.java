package com.booklog.booklog.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserDto {

    String id;
    String name;
    String email;
    String profileImgUrl;
    LocalDate birthday;
    String introduction;
}
