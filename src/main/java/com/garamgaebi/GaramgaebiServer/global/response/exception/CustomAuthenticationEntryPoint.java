package com.garamgaebi.GaramgaebiServer.global.response.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
// 인증에 실패하는 경우
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 로그 찍기
        ErrorCode exception = (ErrorCode)request.getAttribute("exception");

        if(exception == null) {
            setErrorResponse(response, ErrorCode.EMPTY_JWT_TOKEN);
        }
        else {
            setErrorResponse(response, exception);
        }

    }
    private void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        BaseResponse errorResponse = new BaseResponse(errorCode);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
