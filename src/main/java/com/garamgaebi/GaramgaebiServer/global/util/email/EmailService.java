package com.garamgaebi.GaramgaebiServer.global.util.email;

import com.garamgaebi.GaramgaebiServer.domain.member.dto.SendEmailReq;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.EmailRes;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.VerifyEmailReq;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.garamgaebi.GaramgaebiServer.global.util.RedisUtil;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;
    private final MemberRepository memberRepository;

    public static String ePw = null;

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
    public static void createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 숫자 6자리
            key.append((rnd.nextInt(10)));
        }

        ePw = key.toString();
    }

    @Async
    public void sendEmail(SendEmailReq sendEmailReq) throws Exception {
        Optional<Member> member = memberRepository.findByUniEmail(sendEmailReq.getEmail());
        if (member.isPresent()) {
            throw new RestApiException(ErrorCode.ALREADY_EXIST_UNI_EMAIL);
        }

        createKey(); // 인증번호 생성

        redisUtil.setDataExpire(sendEmailReq.getEmail(), ePw, 60 * 3L); // 유효시간 3분

        MimeMessage message = createMessage(sendEmailReq.getEmail()); // 메세지 생성
        try {
            emailSender.send(message); // 이메일 전송
        } catch(MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public EmailRes verifyEmail(VerifyEmailReq verifyEmailReq) {
        String savedKey = redisUtil.getData(verifyEmailReq.getEmail());
        if (savedKey != null) {
            System.out.println("savedKey: " + savedKey);
            System.out.println("reqKey: " + verifyEmailReq.getKey());
            if (savedKey.equals(verifyEmailReq.getKey())) {
                redisUtil.deleteData(verifyEmailReq.getEmail());
            } else {
                throw new RestApiException(ErrorCode.NOT_CORRECT_VERIFY);
            }
        } else {
            throw new RestApiException(ErrorCode.NOT_EXIST_VERIFY_EMAIL);
        }

        EmailRes res = new EmailRes("인증에 성공하였습니다.");

        return res;
    }
}
