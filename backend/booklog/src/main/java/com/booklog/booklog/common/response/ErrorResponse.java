package com.booklog.booklog.common.response;

import com.booklog.booklog.common.code.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    private final int code;
    private final String error;
    private final String message;
    private final ResponseEnum result = ResponseEnum.FAIL;

    // Errors가 없다면 응답이 내려가지 않게 처리
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> errors;

    private ErrorResponse(ErrorCode code, List<FieldError> fieldErrors) {
        this.code = code.getHttpStatus().value();
        this.error = code.getHttpStatus().name();
        this.message = code.getMessage();
        this.errors = fieldErrors;
    }

    private ErrorResponse(ErrorCode code) {
        this.code = code.getHttpStatus().value();
        this.error = code.getHttpStatus().name();
        this.message = code.getMessage();
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    @Getter
    @Builder
    public static class FieldError {
        private String field;
        private String message;

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream().map(error ->
                            FieldError.builder()
                                    .field(error.getField())
                                    .message(error.getDefaultMessage())
                                    .build())
                    .collect(Collectors.toList());
        }
    }
}