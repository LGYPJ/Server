package com.garamgaebi.GaramgaebiServer.domain.member.controller;


import com.garamgaebi.GaramgaebiServer.domain.member.dto.SendEmailReq;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.EmailRes;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.VerifyEmailReq;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.garamgaebi.GaramgaebiServer.global.util.email.EmailService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

//@RestController
@Tag(name = "EmailController", description = "이메일 컨트롤러(담당자:애플)")
@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) { this.emailService = emailService; }

    @Operation(summary = "이메일 전송", description = "request에 담긴 이메일로 인증번호 전송", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/sendEmail")
    public void sendEmail(@Validated @RequestBody SendEmailReq emailReq) throws Exception {
        emailService.sendEmail(emailReq);
    }

    @Operation(summary = "이메일 인증", description = "request에 담긴 인증번호로 이메일 인증", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/verify")
    public BaseResponse<EmailRes> verifyEmail(@RequestBody VerifyEmailReq verifyEmailReq) {
        return new BaseResponse<>(emailService.verifyEmail(verifyEmailReq));
    }
}
