package com.garamgaebi.GaramgaebiServer.domain.member.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRolesRepository;
import com.garamgaebi.GaramgaebiServer.global.security.JwtTokenProvider;
import com.garamgaebi.GaramgaebiServer.global.security.dto.TokenInfo;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.garamgaebi.GaramgaebiServer.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberRolesRepository memberRolesRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final RedisUtil redisUtil;


    private boolean checkNicknameValidation(String nickname) {
//        if (nickname.length() > 8) {
//            return false;
//        }
//
//        String pattern = String.valueOf(Pattern.compile("^[a-zA-Z0-9]*$"));  // 영문자와 숫자만 있는지 확인
//
//        boolean result = Pattern.matches(pattern, nickname);
//        System.out.println(result);
//
//        return result;

        return true;
    }

    @Transactional
    public PostMemberRes postMember(PostMemberReq postMemberReq) {
        if (checkNicknameValidation(postMemberReq.getNickname())) { // 유효한 닉네임
            // 이미 존재하는 학교 이메일인지 확인
            Optional<Member> member = memberRepository.findByUniEmail(postMemberReq.getUniEmail());
            if (member.isEmpty() == false) {
                throw new RestApiException(ErrorCode.ALREADY_EXIST_UNI_EMAIL);
            }

            PostMemberRes postMemberRes = new PostMemberRes(memberRepository.save(postMemberReq.toEntity()).getMemberIdx());
            MemberRolesDto memberRolesDto = new MemberRolesDto();
            memberRolesDto.setMemberIdx(postMemberRes.getMemberIdx());
            memberRolesDto.setRoles("USER");
            memberRolesRepository.save(memberRolesDto.toEntity());
            return postMemberRes;
        } else { // 유효하지 않은 닉네임
            throw new RestApiException(ErrorCode.INVALID_NICKNAME);
        }

    }

    @Transactional
    public InactivedMemberRes inactivedMember(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

        member.inactivedMember();

        return new InactivedMemberRes(true);
    }

    @Transactional
    public TokenInfo login(MemberLoginReq memberLoginReq) {
        // 1. ID/PW를 기반으로 Authentication 객체 생성
        // 이 때, authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberLoginReq.getUniEmail(), memberLoginReq.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)
        // authenticate 메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT Token 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisUtil.setDataExpire("RT: " + authentication.getName(),
                tokenInfo.getRefreshToken(),
                tokenInfo.getRefreshTokenExpirationTime());

        // MemberLoginReq.uniEmail로 MemberIdx 조회 및 반환
        Member member = memberRepository.findByUniEmail(memberLoginReq.getUniEmail())
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));
        tokenInfo.setMemberIdx(member.getMemberIdx());

        return tokenInfo;
    }

    public MemberLogoutRes logout(MemberLogoutReq memberLogoutReq) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(memberLogoutReq.getAccessToken())) {
            throw new RestApiException(ErrorCode.INVALID_NICKNAME);
        }

        // 2. Access Token에서 User email을 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(memberLogoutReq.getAccessToken());

        // 3. Redis에서 해당 User email로 저장된 Refresh Token이 있는지 여부를 확인한 후 있으면 삭제
        if (redisUtil.getData("RT: " + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisUtil.deleteData("RT: " + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장
        Long expiration = jwtTokenProvider.getExpiration(memberLogoutReq.getAccessToken());
        redisUtil.setDataExpire(memberLogoutReq.getAccessToken(),
                "logout",
                expiration);

        MemberLogoutRes memberLogoutRes = new MemberLogoutRes();
        memberLogoutRes.setMemberInfo(authentication.getName());

        return memberLogoutRes;
    }
}
