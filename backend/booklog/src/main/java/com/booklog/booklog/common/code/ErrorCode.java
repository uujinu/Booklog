package com.booklog.booklog.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 200 OK : 성공 */
    SUCCESS(HttpStatus.OK, "요청에 성공하였습니다."),

    /* 400 BAD_REQUEST : 잘못된 요청 */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자의 요청 */
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    INVALID_CODE(HttpStatus.UNAUTHORIZED, "인증 코드가 일치하지 않습니다."),

    /* 403 FORBIDDEN : 권한이 없는 사용자의 요청 */
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    EXPIRED_CODE(HttpStatus.FORBIDDEN, "인증 코드가 만료되었습니다."),

    /* 404 NOT_FOUND : 리소스를 찾을 수 없음 */
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),

    /* 405 METHOD_NOT_ALLOWED : 허용되지 않은 Request Method 호출 */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 요청입니다."),

    /* 409 DUPLICATE : 중복된 리소스 */
    RESOURCE_DUPLICATE(HttpStatus.CONFLICT, "중복된 리소스입니다."),
    NAME_DUPLICATE(HttpStatus.CONFLICT, "중복된 닉네임입니다."),
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    USER_DUPLICATE(HttpStatus.CONFLICT, "이미 등록된 회원입니다."),

    /* 500 INTERNAL_SERVER_ERROR: 내부 서버 오류 */
    EMAIL_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다."),
    VERIFICATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "인증에 실패했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "시스템 오류입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}