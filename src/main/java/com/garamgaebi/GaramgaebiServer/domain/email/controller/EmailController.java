package com.garamgaebi.GaramgaebiServer.domain.email.controller;


import com.garamgaebi.GaramgaebiServer.domain.email.dto.SendEmailReq;
import com.garamgaebi.GaramgaebiServer.domain.email.dto.EmailRes;
import com.garamgaebi.GaramgaebiServer.domain.email.dto.VerifyEmailReq;
import com.garamgaebi.GaramgaebiServer.domain.email.service.EmailService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@RestController
@Tag(name = "EmailController", description = "이메일 컨트롤러(담당자:애플)")
@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) { this.emailService = emailService; }

    @PostMapping("/sendEmail")
    public BaseResponse<EmailRes> sendEmail(@RequestBody SendEmailReq emailReq) throws Exception {
        return new BaseResponse<>(emailService.sendEmail(emailReq));
    }

    @PostMapping("/verify")
    public BaseResponse<EmailRes> verifyEmail(@RequestBody VerifyEmailReq verifyEmailReq) {
        return new BaseResponse<>(emailService.verifyEmail(verifyEmailReq));
    }
}
