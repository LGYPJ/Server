package com.garamgaebi.GaramgaebiServer.member.service;

import com.garamgaebi.GaramgaebiServer.domain.member.service.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberServiceImpl memberService;

    @Test
    public void postMemberWithKakao() {

    }

    @Test
    public void postMemberWithApple() {

    }

    @Test
    public void inactivedMember() {

    }

    @Test
    public void loginWithKakao() {

    }

    @Test
    public void loginWithApple() {

    }

    @Test
    public void autoLogin() {

    }

    @Test
    public void logout() {

    }

    @Test
    public void sendEmail() {

    }
}
