package com.garamgaebi.GaramgaebiServer.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<?> handleRestApiException(RestApiException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception e) {
        return handleExceptionInternal(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(createExceptionResponse(errorCode));
    }

    private ExceptionResponse createExceptionResponse(ErrorCode errorCode) {
        return ExceptionResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    @Builder
    @Getter
    private static class ExceptionResponse {
        private String code;
        private String message;
    }
}
