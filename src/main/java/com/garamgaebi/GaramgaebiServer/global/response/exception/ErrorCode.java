package com.garamgaebi.GaramgaebiServer.global.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {


    EMPTY_PARAMETER(2000, "파라미터가 전달되지 않았습니다."),
    NOT_EXIST_MEMBER(2001, "존재하지 않는 회원입니다."),
    NOT_EXIST_PROGRAM(2002, "존재하지 않는 프로그램입니다."),

    INTERNAL_SERVER_ERROR(500, "서버 오류입니다.");



    private final Integer code;
    private final String message;
}
