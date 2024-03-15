package com.booklog.booklog.domain.user.dto;

import com.booklog.booklog.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignUpReqDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야합니다.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotNull(message = "생년월일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    // dto -> entity
    public User toEntity(String profileImgUrl) {
        return User.builder().name(name).password(password).email(email).profileImgUrl(profileImgUrl).birthday(birthday).build();
    }
}