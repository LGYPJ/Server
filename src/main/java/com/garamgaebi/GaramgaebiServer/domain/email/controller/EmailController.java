package com.garamgaebi.GaramgaebiServer.domain.email.controller;


import com.garamgaebi.GaramgaebiServer.domain.email.dto.EmailReq;
import com.garamgaebi.GaramgaebiServer.domain.email.dto.EmailRes;
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

    @PostMapping("/emailconfirm")
    public BaseResponse<EmailRes> emailConfirm(@RequestBody EmailReq emailReq) throws Exception {
        return new BaseResponse<>(emailService.sendEmail(emailReq));
    }
}
