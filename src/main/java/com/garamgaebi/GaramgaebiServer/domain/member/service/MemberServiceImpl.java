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


    // ?????? ??????
    @Transactional
    public PostMemberRes postMemberWithKakao(PostMemberKakaoReq postMemberKakaoReq) throws IOException {
        Map<String, Object> result = kakaoService.getUserInfo(postMemberKakaoReq.getAccessToken());
        String identifier = result.get("id").toString();

        // ?????? ???????????? ???????????? ??????
        Optional<Member> memberByIdentifier = memberRepository.findByIdentifier(identifier);
        if (memberByIdentifier.isEmpty() == false) {
            throw new RestApiException(ErrorCode.ALREADY_EXIST_IDENTIFIER);
        }

        // ?????? ???????????? ?????? ??????????????? ??????
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

        // ?????? ???????????? ???????????? ??????
        Optional<Member> memberByIdentifier = memberRepository.findByIdentifier(identifier);
        if (memberByIdentifier.isEmpty() == false) {
            throw new RestApiException(ErrorCode.ALREADY_EXIST_IDENTIFIER);
        }

        // ?????? ???????????? ?????? ??????????????? ??????
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

    // ?????? ??????
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

    // ?????? ?????????
    @Transactional
    public LoginRes loginWithKakao(MemberKakaoLoginReq memberKakaoLoginReq) throws IOException {
        Map<String, Object> result = kakaoService.getUserInfo(memberKakaoLoginReq.getAccessToken());
        String identifier = result.get("id").toString();

        Member member = memberRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

        if (member.getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.INACTIVE_MEMBER);
        }

        // 1. ID/PW??? ???????????? Authentication ?????? ??????
        // ??? ???, authentication??? ?????? ????????? ???????????? authenticated ?????? false
        Long memberIdx = member.getMemberIdx();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(identifier, memberIdx.toString());

        // 2. ?????? ?????? (????????? ???????????? ??????)
        // authenticate ???????????? ????????? ??? CustomUserDetailsService?????? ?????? loadUserByUsername ???????????? ??????
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. ?????? ????????? ???????????? JWT Token ??????
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, memberIdx);
        tokenInfo.setMemberIdx(memberIdx);

        // 4. RefreshToken Redis ?????? (expirationTime ????????? ?????? ?????? ?????? ??????)
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

        // 1. ID/PW??? ???????????? Authentication ?????? ??????
        // ??? ???, authentication??? ?????? ????????? ???????????? authenticated ?????? false
        Long memberIdx = member.getMemberIdx();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(identifier, memberIdx.toString());

        // 2. ?????? ?????? (????????? ???????????? ??????)
        // authenticate ???????????? ????????? ??? CustomUserDetailsService?????? ?????? loadUserByUsername ???????????? ??????
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. ?????? ????????? ???????????? JWT Token ??????
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, memberIdx);
        tokenInfo.setMemberIdx(memberIdx);

        // 4. RefreshToken Redis ?????? (expirationTime ????????? ?????? ?????? ?????? ??????)
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
        if (jwtTokenProvider.getExpiration(refreshToken) < 0) { // ????????? ??????
            throw new RestApiException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        TokenRefreshRes result = jwtTokenProvider.refresh(refreshToken);

        return new LoginRes(
                result.getTokenInfo(),
                result.getMember().getUniEmail(),
                result.getMember().getNickname()
        );
    }


    // ?????? ????????????
    public MemberLogoutRes logout(MemberLogoutReq memberLogoutReq) {
        // 1. Access Token ??????
        if (!jwtTokenProvider.validateToken(memberLogoutReq.getAccessToken())) {
            throw new RestApiException(ErrorCode.INVALID_JWT_TOKEN);
        }

        // 2. Access Token?????? User email??? ?????????
        Authentication authentication = jwtTokenProvider.getAuthentication(memberLogoutReq.getAccessToken());

        // 3. Redis?????? ?????? User email??? ????????? Refresh Token??? ????????? ????????? ????????? ??? ????????? ??????
        if (redisUtil.getData("RT: " + authentication.getName()) != null) {
            // Refresh Token ??????
            redisUtil.deleteData("RT: " + authentication.getName());
        }

        // 4. ?????? Access Token ??????????????? ????????? ?????? BlackList??? ??????
        Long expiration = jwtTokenProvider.getExpiration(memberLogoutReq.getAccessToken());
        redisUtil.setDataExpire(memberLogoutReq.getAccessToken(),
                "logout",
                expiration);

        MemberLogoutRes memberLogoutRes = new MemberLogoutRes(authentication.getName());

        if(memberLogoutReq.getFcmToken() != null) {
            Member member = memberRepository.findByIdentifier(authentication.getName()).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

            member.deleteMemberFcm(memberLogoutReq.getFcmToken());
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
