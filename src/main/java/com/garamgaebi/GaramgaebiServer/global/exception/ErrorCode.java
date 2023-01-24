package com.garamgaebi.GaramgaebiServer.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    EMPTY_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터가 전달되지 않았습니다."),
    NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    NOT_EXIST_PROGRAM(HttpStatus.BAD_REQUEST, "존재하지 않는 프로그램입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다.");



    private final HttpStatus httpStatus;
    private final String message;
}
