package com.garamgaebi.GaramgaebiServer.domain.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.garamgaebi.GaramgaebiServer.domain.login.dto.SocialUserInfoDto;
import com.garamgaebi.GaramgaebiServer.domain.login.service.KakaoUserService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialLoginController {
    private final KakaoUserService kakaoUserService;

    @Autowired
    public SocialLoginController(KakaoUserService kakaoUserService) { this.kakaoUserService = kakaoUserService; }

    @GetMapping("/member/kakao/callback")
    public BaseResponse<SocialUserInfoDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return new BaseResponse<>(kakaoUserService.kakaoLogin(code, response));
    }
}
