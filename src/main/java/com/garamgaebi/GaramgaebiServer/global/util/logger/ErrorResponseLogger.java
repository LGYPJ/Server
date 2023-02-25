package com.garamgaebi.GaramgaebiServer.global.util.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ErrorResponseLogger {

    @Pointcut("within(com.garamgaebi.GaramgaebiServer.global.response.exception.GlobalExceptionHandler)")
    public void onErrorResponse() {}

    @Around("com.garamgaebi.GaramgaebiServer.global.util.logger.ErrorResponseLogger.onErrorResponse()")
    public Object doErrorLoging(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }finally {
            log.info("response : {}", mapper.registerModule(new JavaTimeModule()).writeValueAsString(result));
        }
        return result;
    }
}
