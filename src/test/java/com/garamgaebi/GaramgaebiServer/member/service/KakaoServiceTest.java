package com.garamgaebi.GaramgaebiServer.member.service;

import com.garamgaebi.GaramgaebiServer.domain.member.service.KakaoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class KakaoServiceTest {
    @Autowired
    KakaoServiceImpl kakaoService;

    @Test
    public void getToken() {

    }

    @Test
    public void getUserInfo() {

    }
}
