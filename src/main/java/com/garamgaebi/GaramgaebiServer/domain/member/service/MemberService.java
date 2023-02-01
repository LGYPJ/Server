package com.garamgaebi.GaramgaebiServer.domain.member.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.InactivedMemberRes;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.MemberLoginReq;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberReq;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberRes;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.global.config.security.JwtTokenProvider;
import com.garamgaebi.GaramgaebiServer.global.config.security.dto.TokenInfo;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

//    @Value("${jwt.secret}")
//    private final String secret;

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
            PostMemberRes postMemberRes = new PostMemberRes(memberRepository.save(postMemberReq.toEntity()).getMemberIdx());
            return postMemberRes;
        } else { // 유효하지 않은 닉네임
            throw new RestApiException(ErrorCode.INVALID_NICKNAME);
        }

    }

    @Transactional
    public InactivedMemberRes inactivedMember(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

        System.out.println(member.getStatus());

        member.inactivedMember();

        System.out.println(member.getStatus());

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

        return tokenInfo;
    }
}
