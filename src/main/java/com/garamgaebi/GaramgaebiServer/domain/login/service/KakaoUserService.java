package com.garamgaebi.GaramgaebiServer.domain.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
//import com.garamgaebi.GaramgaebiServer.domain.login.component.JwtTokenUtil;
import com.garamgaebi.GaramgaebiServer.domain.login.dto.SocialUserInfoDto;
//import com.garamgaebi.GaramgaebiServer.domain.login.dto.UserDetailsImpl;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberReq;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;


//@Service
@PropertySource("classpath:application.properties")
public class KakaoUserService {
    private final MemberRepository memberRepository;

    public KakaoUserService(MemberRepository memberRepository) { this.memberRepository = memberRepository; }

    @Value("${social.kakao.client_id}")
    private String CLIENT_ID;

    @Value("${social.kakao.client_secret}")
    private String CLIENT_SECRET;

    @Value("${social.kakao.redirect_uri}")
    private String REDIRECT_URI;


    public SocialUserInfoDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. request "access token" with "code"
        String accessToken = getAccessToken(code);

        // 2. call kakao api with token
        SocialUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

//        // 3. member register with kakaoID
//        Member kakaoMember = registerKakaoUserIfNeed(kakaoUserInfo);
//
//        // 4. 강제 login
//        Authentication authentication = forceLogin(kakaoMember);
//
//        // 5. response Header에 JWT token 추가
//        kakaoUsersAuthorizationInput(authentication, response);

        return kakaoUserInfo;
    }

    // 1. request "access token" with "code"
    private String getAccessToken(String code) throws JsonProcessingException {
        // create HTTP Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // create HTTP Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", code);

        // request HTTP
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // response HTTP (JSON) -> parsing access token
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. call kakao api with token
    private SocialUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // create HTTP Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // request HTTP
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // response HTTP
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        return new SocialUserInfoDto(id, nickname, email);
    }

//    // 3. member register with kakaoID
//    private Member registerKakaoUserIfNeed(SocialUserInfoDto kakaoUserInfo) {
//        // 중복된 email 확인
//        String kakaoEmail = kakaoUserInfo.getEmail();
//        String nickname = kakaoUserInfo.getNickname();
//        Member kakaoMember = memberRepository.findByEmail(kakaoEmail).orElse(null);
//
//        if (kakaoMember == null) {
//            // register
//            // 카카오 로그인 다 처리된 이후에 데이터를 넣어야 할 거 같은디 ,, ?
//            // userInfo return하고 member post api 따로 호출하게 하든지 -> 이게 낫겠당
//            // kakaoEmail table을 따로 두고 member post 유무를 분간해서 false일 경우 회원가입을 진행하게 하든지
//
//        }
//
//        return kakaoMember;
//    }
//
//    // 4. 강제 login
//    private Authentication forceLogin(Member kakaoMember) {
//        UserDetails userDetails = new UserDetailsImpl(kakaoMember);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return authentication;
//    }
//
//    // 5. response Header에 JWT token 추가
//    private void kakaoUsersAuthorizationInput(Authentication authentication, HttpServletResponse response) {
//        // response header에 token 추가
//        UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
//
//        String token = jwtTokenUtil.generateToken(userDetailsImpl);
//        response.addHeader("Authorization", "BEARER" + " " + token);
//    }
}
