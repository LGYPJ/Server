package com.garamgaebi.GaramgaebiServer.global.response.exception;


import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<?> handleRestApiException(RestApiException e) {
        log.error("ERROR : {}", e.getErrorCode());
        return new ResponseEntity<>(new BaseResponse<>(e.getErrorCode()), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("METHOD ARGUMENT NOT VALID ERROR : ", ex);
        return new ResponseEntity<>(new BaseResponse<>(ErrorCode.INVALID_ARGUMENT), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception e) {
        log.error("UNKNOWN ERROR : ", e);
        return new ResponseEntity<>(new BaseResponse<>(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.OK);
    }
}
