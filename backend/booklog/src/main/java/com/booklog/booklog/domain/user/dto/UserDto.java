package com.booklog.booklog.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserDto {

    String id;
    String name;
    String email;
    String profileImgUrl;
    LocalDate birthday;
    String introduction;
}
