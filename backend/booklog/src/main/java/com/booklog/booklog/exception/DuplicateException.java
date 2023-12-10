package com.booklog.booklog.exception;

import com.booklog.booklog.common.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicateException extends IllegalArgumentException {

    private final ErrorCode errorCode;
}