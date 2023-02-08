package com.garamgaebi.GaramgaebiServer.domain.email.service;

import com.garamgaebi.GaramgaebiServer.domain.email.dto.EmailReq;
import com.garamgaebi.GaramgaebiServer.domain.email.dto.EmailRes;
import io.netty.util.internal.ThreadLocalRandom;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    public static final String ePw = createKey();

    private MimeMessage createMessage(String to) throws Exception {
        System.out.println("받는 사람: " + to);
        System.out.println("인증 번호: " + ePw);

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to); // 받는 사람
        message.setSubject("가람개비 회원가입 이메일 인증"); // 메일 제목

        String msgg = "";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 가람개비입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요.<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("gachon2023@gmail.com","Garamgaebi")); //보내는 사람

        return message;
    }
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        key.delete(0, key.length());
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 숫자 6자리
            key.append((rnd.nextInt(10)));
        }

        return key.toString();
    }

    public EmailRes sendEmail(EmailReq emailReq) throws Exception {
        MimeMessage message = createMessage(emailReq.getEmail());
        try {
            emailSender.send(message);
        } catch(MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        EmailRes res = new EmailRes();
        res.setKey(ePw);

        return res;
    }
}
