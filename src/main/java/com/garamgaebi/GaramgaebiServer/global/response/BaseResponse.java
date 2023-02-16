package com.garamgaebi.GaramgaebiServer.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.SerializableString;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@Schema(description = "기본 응답 메세지 포맷")
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {

    @Schema(description = "요청 성공 여부(true/false)")
    private final Boolean isSuccess;
    @Schema(description = "응답 코드")
    private final Integer code;
    @Schema(description = "응답 메세지")
    private final String message;
    @Schema(description = "응답 결과")
    //@JsonInclude(JsonInclude.Include.NON_NULL)
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
