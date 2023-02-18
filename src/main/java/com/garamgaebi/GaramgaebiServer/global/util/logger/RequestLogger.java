package com.garamgaebi.GaramgaebiServer.global.util.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class RequestLogger {

    @Pointcut("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public void onRequest() {}

    @Around("com.garamgaebi.GaramgaebiServer.global.util.logger.RequestLogger.onRequest()")
    public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String controllerName = joinPoint.getSignature().getDeclaringType().getName();
            String methodName = joinPoint.getSignature().getName();
            Map<String, Object> params = new HashMap<>();

            try {
                params.put("controller", controllerName);
                params.put("method", methodName);
                params.put("params", mapper.registerModule(new JavaTimeModule()).writeValueAsString(getParams(joinPoint)));
                params.put("http-method", request.getMethod());
                params.put("request-uri", request.getRequestURI());
            } catch (Exception e) {
                log.error("로그 AOP 에러", e);
            }

            log.info("request : {}", params);

            result = joinPoint.proceed();

            return result;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            if(result != null)
                log.info("response : {}", mapper.writeValueAsString(result));
        }
    }

    private Map getParams(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], args[i]);
        }
        return params;
    }
}
