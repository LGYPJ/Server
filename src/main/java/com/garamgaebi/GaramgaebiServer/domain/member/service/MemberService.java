package com.garamgaebi.GaramgaebiServer.domain.member.service;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.MemberFcm;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.MemberRoles;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.vo.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.*;
import com.garamgaebi.GaramgaebiServer.global.security.dto.TokenInfo;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {
    /* 멤버 가입 */
    public PostMemberRes postMember(PostMemberReq postMemberReq);

    /* 멤버 탈퇴 */
    public InactivedMemberRes inactivedMember(InactivedMemberReq inactivedMemberReq);

    /* 멤버 로그인 */
    public TokenInfo login(MemberLoginReq memberLoginReq);
    
    /* 멤버 로그아웃 */
    public MemberLogoutRes logout(MemberLogoutReq memberLogoutReq);

}
