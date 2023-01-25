package com.garamgaebi.GaramgaebiServer.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {

    private final Boolean isSuccess;
    private final Integer code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public BaseResponse(ErrorCode errorCode) {
        this.isSuccess = false;
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    public BaseResponse(T result) {
        this.isSuccess = true;
        this.message = "요청에 성공하였습니다.";
        this.code = 200;
        this.result = result;
    }
}
