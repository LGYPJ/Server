package com.garamgaebi.GaramgaebiServer.global.response.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//액세스 권한이 없는 리소스에 접근하는 경우
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 로그 찍기
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        BaseResponse errorResponse = new BaseResponse(ErrorCode.NOT_AUTHORIZED_ACCESS);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}