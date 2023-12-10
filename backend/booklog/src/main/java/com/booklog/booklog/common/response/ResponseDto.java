package com.booklog.booklog.common.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    private T data;
    private ResponseEnum result;

    public static <T> ResponseDto<T> of(T data, ResponseEnum result) {
        return new ResponseDto<T>(data, result);
    }

    public static <T> ResponseDto<T> of(T data) {
        return new ResponseDto<T>(data, ResponseEnum.SUCCESS);
    }

    public static ResponseDto<Boolean> ofSuccess() {
        return new ResponseDto<>(Boolean.TRUE, ResponseEnum.SUCCESS);
    }

    public static ResponseDto<Boolean> ofFail() {
        return new ResponseDto<>(Boolean.FALSE, ResponseEnum.FAIL);
    }
}
