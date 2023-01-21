package com.garamgaebi.GaramgaebiServer.domain.email.controller;


import com.garamgaebi.GaramgaebiServer.domain.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("emailconfirm")
    @ResponseBody
    public String emailConfirm(@RequestParam("email") String email) throws Exception {
        String confirm = emailService.sendEmail(email);

        return confirm;
    }
}
