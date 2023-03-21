package com.garamgaebi.GaramgaebiServer.domain.member.service;

import com.garamgaebi.GaramgaebiServer.domain.member.dto.*;
import com.garamgaebi.GaramgaebiServer.global.security.dto.TokenInfo;

import java.io.IOException;

public interface MemberService {
    /* 멤버 가입 */
    public PostMemberRes postMemberWithKakao(PostMemberKakaoReq postMemberKakaoReq) throws IOException;
    public PostMemberRes postMemberWithApple(PostMemberAppleReq postMemberAppleReq) throws IOException;

    /* 멤버 탈퇴 */
    public InactivedMemberRes inactivedMember(InactivedMemberReq inactivedMemberReq);

    /* 멤버 로그인 */
    public LoginRes loginWithKakao(MemberKakaoLoginReq memberKakaoLoginReq) throws IOException;
    public LoginRes loginWithApple(MemberAppleLoginReq memberAppleLoginReq) throws IOException;
    public LoginRes autoLogin(String refreshToken);
    
    /* 멤버 로그아웃 */
    public MemberLogoutRes logout(MemberLogoutReq memberLogoutReq);

    /* 이메일 인증번호 전송 */
    public Boolean sendEmail(SendEmailReq sendEmailReq);
}
