package com.booklog.booklog.domain.user.entity;

import com.booklog.booklog.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@SuperBuilder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    private static String INTRODUCTION_DEFAULT = "Booklog와 함께 즐거운 독서해요!";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Column(length = 50, unique = true)
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Column(unique = true)
    private String email;

    @Nullable
    private String profileImgUrl;

    @NotNull
    private LocalDate birthday;

    @Column(length = 200)
    @Builder.Default
    private String introduction = INTRODUCTION_DEFAULT;

    public void setName(String name) { this.name = name; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileImgUrl(String url) { this.profileImgUrl = url; }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}