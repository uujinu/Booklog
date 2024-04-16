package com.booklog.booklog.domain.user.dto;

import com.booklog.booklog.domain.user.entity.User;
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

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .email(user.getEmail())
                .profileImgUrl(user.getProfileImgUrl())
                .birthday(user.getBirthday())
                .introduction(user.getIntroduction())
                .build();
    }

}
