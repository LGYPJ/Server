package com.garamgaebi.GaramgaebiServer.domain.member.service;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.MemberFcm;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.MemberRoles;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.vo.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberQuitRepository;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRolesRepository;
import com.garamgaebi.GaramgaebiServer.global.security.JwtTokenProvider;
import com.garamgaebi.GaramgaebiServer.global.security.dto.TokenInfo;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.garamgaebi.GaramgaebiServer.global.util.RedisUtil;
import com.garamgaebi.GaramgaebiServer.global.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberRolesRepository memberRolesRepository;
    private final MemberQuitRepository memberQuitRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final KakaoService kakaoService;
    private final AppleService appleService;
    private final RedisUtil redisUtil;


    // 멤버 가입
    @Transactional
    public PostMemberRes postMemberWithKakao(PostMemberKakaoReq postMemberKakaoReq) throws IOException {
        Map<String, Object> result = kakaoService.getUserInfo(postMemberKakaoReq.getAccessToken());
        String identifier = result.get("id").toString();

        // 이미 존재하는 유저인지 확인
        Optional<Member> memberByIdentifier = memberRepository.findByIdentifier(identifier);
        if (memberByIdentifier.isEmpty() == false) {
            throw new RestApiException(ErrorCode.ALREADY_EXIST_IDENTIFIER);
        }

        // 이미 존재하는 학교 이메일인지 확인
        Optional<Member> memberByUni = memberRepository.findByUniEmail(postMemberKakaoReq.getUniEmail());
        if (memberByUni.isEmpty() == false) {
            throw new RestApiException(ErrorCode.ALREADY_EXIST_UNI_EMAIL);
        }

        Member member = memberRepository.save(postMemberKakaoReq.toEntity(identifier));
        Long memberIdx = member.getMemberIdx();

        MemberRolesDto memberRolesDto = new MemberRolesDto(memberIdx, "USER");
        memberRolesRepository.save(memberRolesDto.toEntity());


        PostMemberRes postMemberRes = new PostMemberRes(memberIdx);
        return postMemberRes;
    }

    @Transactional
    public PostMemberRes postMemberWithApple(PostMemberAppleReq postMemberAppleReq) throws IOException {
        String identifier = appleService.getUserIdFromApple(postMemberAppleReq.getIdToken());

        // 이미 존재하는 유저인지 확인
        Optional<Member> memberByIdentifier = memberRepository.findByIdentifier(identifier);
        if (memberByIdentifier.isEmpty() == false) {
            throw new RestApiException(ErrorCode.ALREADY_EXIST_IDENTIFIER);
        }

        // 이미 존재하는 학교 이메일인지 확인
        Optional<Member> memberByUni = memberRepository.findByUniEmail(postMemberAppleReq.getUniEmail());
        if (memberByUni.isEmpty() == false) {
            throw new RestApiException(ErrorCode.ALREADY_EXIST_UNI_EMAIL);
        }

        Member member = memberRepository.save(postMemberAppleReq.toEntity(identifier));
        Long memberIdx = member.getMemberIdx();

        MemberRolesDto memberRolesDto = new MemberRolesDto(memberIdx, "USER");
        memberRolesRepository.save(memberRolesDto.toEntity());


        PostMemberRes postMemberRes = new PostMemberRes(memberIdx);
        return postMemberRes;
    }

    // 멤버 탈퇴
    @Transactional
    public InactivedMemberRes inactivedMember(InactivedMemberReq inactivedMemberReq) {
        Member member = memberRepository.findById(inactivedMemberReq.getMemberIdx())
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));
        MemberRoles memberRoles = memberRolesRepository.findByMemberIdx(inactivedMemberReq.getMemberIdx())
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

        if(member.getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        member.inactivedMember();
        memberRoles.inactivedMember();
        memberQuitRepository.save(inactivedMemberReq.toEntity());

        return new InactivedMemberRes(true);
    }

    // 멤버 로그인
    @Transactional
    public LoginRes loginWithKakao(MemberKakaoLoginReq memberKakaoLoginReq) throws IOException {
        Map<String, Object> result = kakaoService.getUserInfo(memberKakaoLoginReq.getAccessToken());
        String identifier = result.get("id").toString();

        System.out.println("id: "  + identifier);

        Member member = memberRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

        if (member.getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.INACTIVE_MEMBER);
        }

        // 1. ID/PW를 기반으로 Authentication 객체 생성
        // 이 때, authentication은 인증 여부를 확인하는 authenticated 값이 false
        Long memberIdx = member.getMemberIdx();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(identifier, memberIdx.toString());

        // 2. 실제 검증 (사용자 비밀번호 체크)
        // authenticate 메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT Token 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, memberIdx);
        tokenInfo.setMemberIdx(memberIdx);

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisUtil.setDataExpire("RT: " + authentication.getName(),
                tokenInfo.getRefreshToken(),
                tokenInfo.getRefreshTokenExpirationTime());

        if(memberKakaoLoginReq.getFcmToken() != null && !memberKakaoLoginReq.getFcmToken().isBlank()
                && member.getMemberFcms().stream().noneMatch(memberFcm -> memberFcm.getFcmToken().equals(memberKakaoLoginReq.getFcmToken()))) {
            member.addMemberFcms(MemberFcm.builder()
                    .member(member)
                    .fcmToken(memberKakaoLoginReq.getFcmToken())
                    .build());
            memberRepository.save(member);
        }

        return new LoginRes(tokenInfo, member.getUniEmail(), member.getNickname());
    }

    @Transactional
    public LoginRes loginWithApple(MemberAppleLoginReq memberAppleLoginReq) throws IOException {
        String identifier = appleService.getUserIdFromApple(memberAppleLoginReq.getIdToken());

        Member member = memberRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

        if (member.getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.INACTIVE_MEMBER);
        }

        // 1. ID/PW를 기반으로 Authentication 객체 생성
        // 이 때, authentication은 인증 여부를 확인하는 authenticated 값이 false
        Long memberIdx = member.getMemberIdx();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(identifier, memberIdx.toString());

        // 2. 실제 검증 (사용자 비밀번호 체크)
        // authenticate 메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT Token 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, memberIdx);
        tokenInfo.setMemberIdx(memberIdx);

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisUtil.setDataExpire("RT: " + authentication.getName(),
                tokenInfo.getRefreshToken(),
                tokenInfo.getRefreshTokenExpirationTime());

        if(memberAppleLoginReq.getFcmToken() != null && !memberAppleLoginReq.getFcmToken().isBlank()
                && member.getMemberFcms().stream().noneMatch(memberFcm -> memberFcm.getFcmToken().equals(memberAppleLoginReq.getFcmToken()))) {
            member.addMemberFcms(MemberFcm.builder()
                    .member(member)
                    .fcmToken(memberAppleLoginReq.getFcmToken())
                    .build());
            memberRepository.save(member);
        }

        return new LoginRes(tokenInfo, member.getUniEmail(), member.getNickname());
    }

    public LoginRes autoLogin(String refreshToken) {
        if (jwtTokenProvider.getExpiration(refreshToken) < 0) { // 만료된 토큰
            throw new RestApiException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        TokenRefreshRes result = jwtTokenProvider.refresh(refreshToken);

        return new LoginRes(
                result.getTokenInfo(),
                result.getMember().getUniEmail(),
                result.getMember().getNickname()
        );
    }


    // 멤버 로그아웃
    @Transactional
    public MemberLogoutRes logout(MemberLogoutReq memberLogoutReq) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(memberLogoutReq.getAccessToken())) {
            throw new RestApiException(ErrorCode.INVALID_JWT_TOKEN);
        }

        // 2. Access Token에서 User identifier를 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(memberLogoutReq.getAccessToken());

        System.out.println("DEBUG: authentication.getName() = " + authentication.getName());
        // 3. Redis에서 해당 User email로 저장된 Refresh Token이 있는지 여부를 확인한 후 있으면 삭제
        if (redisUtil.getData("RT: " + authentication.getName()) != null) {
            System.out.println("DEBUG: key 찾음");
            // Refresh Token 삭제
            redisUtil.deleteData("RT: " + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장
        Long expiration = jwtTokenProvider.getExpiration(memberLogoutReq.getAccessToken());
        redisUtil.setDataExpire(memberLogoutReq.getAccessToken(),
                "logout",
                expiration);

        MemberLogoutRes memberLogoutRes = new MemberLogoutRes(authentication.getName());

        if(memberLogoutReq.getFcmToken() != null) {
            Member member = memberRepository.findByIdentifier(authentication.getName()).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

            member.deleteMemberFcm(memberLogoutReq.getFcmToken());
            memberRepository.save(member);
        }

        return memberLogoutRes;
    }

    public Boolean sendEmail(SendEmailReq sendEmailReq) {
        Optional<Member> member = memberRepository.findByUniEmail(sendEmailReq.getEmail());
        if (member.isPresent()) {
            throw new RestApiException(ErrorCode.ALREADY_EXIST_UNI_EMAIL);
        }

        emailService.sendEmailAsync(sendEmailReq);

        return true;
    }
}
