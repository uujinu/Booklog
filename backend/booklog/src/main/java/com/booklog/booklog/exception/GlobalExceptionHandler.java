package com.booklog.booklog.exception;

import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.common.response.ErrorResponse;
import com.booklog.booklog.common.response.ResponseDto;
import com.booklog.booklog.common.response.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateException e) {
        log.warn("DuplicateException: ", e);
        final ErrorResponse res = ErrorResponse.of(e.getErrorCode());
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MethodArgumentNoValidException: ", ex);
        final ErrorResponse res = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, ex.getBindingResult());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RestApiException.class})
    protected ResponseEntity<ErrorResponse> handleRestApiException(RestApiException e) {
        log.error("handleRestApiException throw Exception : {}", e);
        final ErrorResponse res = ErrorResponse.of(e.getErrorCode());
        return new ResponseEntity<>(res, e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException throw Exception : {}", e);
        final ErrorResponse res = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error("Exception: {}", ex);
        final ResponseDto<String> res = ResponseDto.of(ex.getMessage(), ResponseEnum.FAIL);
        return super.handleExceptionInternal(ex, res, headers, status, request);
    }
}


