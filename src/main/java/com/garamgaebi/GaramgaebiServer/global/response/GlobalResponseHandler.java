package com.garamgaebi.GaramgaebiServer.global.response;

import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalResponseHandler<T> extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(RestApiException.class)
    public ErrorCode handleRestApiException(RestApiException e) {
        return e.getErrorCode();
    }

    @ExceptionHandler(Exception.class)
    public ErrorCode handleUnknownException(Exception e) {
        return ErrorCode.INTERNAL_SERVER_ERROR;
    }


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if(body instanceof ErrorCode) {
            return new BaseResponse<>((ErrorCode)body);
        }
        return new BaseResponse<>(body);
    }
}
