package com.garamgaebi.GaramgaebiServer.member.service;

import com.garamgaebi.GaramgaebiServer.global.util.email.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    public void createMessage() {

    }

    @Test
    public void createKey() {

    }

    @Test
    public void sendEmailAsync() {

    }

    @Test
    public void verifyEmail() {

    }
}
