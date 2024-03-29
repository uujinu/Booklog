package com.booklog.booklog.domain.book.entity;

import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BookStatus {

    FINISH("F"), WISH("W"), READING("R"); // 완독, 위시, 읽는 중

    @Getter
    @JsonValue
    private final String status;

    @JsonCreator
    public static BookStatus from(String value) {
        for (BookStatus bookStatus : BookStatus.values()) {
            if (bookStatus.equals(value)) {
                return bookStatus;
            }
        }
        throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
    }

    BookStatus(String status) {
        this.status = status;
    }

    public static BookStatus valueOfStatus(String status) {
        return Arrays.stream(values())
                .filter(value -> value.status.equals(status))
                .findAny()
                .orElse(null);
    }

    public String status() {
        return status;
    }
}
