package com.garamgaebi.GaramgaebiServer.global.response.exception;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {


    INVALID_ARGUMENT(2000, "입력 값을 확인해주세요."),
    NOT_EXIST_MEMBER(2001, "존재하지 않는 회원입니다."),
    NOT_EXIST_PROGRAM(2002, "존재하지 않는 프로그램입니다."),

    INVALID_NICKNAME(2003, "유효하지 않은 닉네임입니다."),

    INVALID_ACCESS_TOKEN(2004, "유효하지 않은 ACCESS_TOKEN입니다."),

    ALREADY_EXIST_UNI_EMAIL(2005, "이미 존재하는 학교 이메일입니다."),

    INVALID_JWT_TOKEN(2006, "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(2007, "만료된 JWT 토큰입니다."),
    EMPTY_JWT_TOKEN(2008, "JWT 토큰이 존재하지 않습니다."),
    NOT_AUTHORIZED_ACCESS(2010, "접근이 거부 되었습니다."),

    FAIL_AUTHORIZED_ACCESS(2011, "인증에 실패하였습니다."),
    NOT_EXIST_SEMINAR(20012, "존재하지 않는 세미나입니다."),
    NOT_EXIST_PREWRAPPER(2013, "존재하지 않는 발표자료입니다."),
    FAIL_ACCESS_PROGRAM(2014, "접근할 수 없는 프로그램입니다."),
    INACTIVE_MEMBER(2015, "이미 신청한 프로그램입니다."),

    NOT_EXIST_GAME_ROOM(2016, "존재하지 않는 게임방입니다."),

    NOT_FOUND(404, "요청하신 페이지를 찾을 수 없습니다."),

    NOT_ACCEPTABLE(406, "등록 상태가 아닙니다."),

    INTERNAL_SERVER_ERROR(500, "서버 오류입니다.");


    private final Integer code;
    private final String message;

}
