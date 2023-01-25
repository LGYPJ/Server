package com.garamgaebi.GaramgaebiServer.global.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {


    INVALID_ARGUMENT(2000, "입력 값을 확인해주세요."),
    NOT_EXIST_MEMBER(2001, "존재하지 않는 회원입니다."),
    NOT_EXIST_PROGRAM(2002, "존재하지 않는 프로그램입니다."),

    NOT_FOUND(404, "요청하신 페이지를 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR(500, "서버 오류입니다.");



    private final Integer code;
    private final String message;
}
