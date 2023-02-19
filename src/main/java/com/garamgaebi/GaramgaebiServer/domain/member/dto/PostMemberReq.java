package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.vo.MemberStatus;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMemberReq {
    private String nickname;
    private String profileEmail;
    private String socialEmail;
    private String uniEmail;
    private MemberStatus status;

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .profileEmail(profileEmail)
                .socialEmail(socialEmail)
                .uniEmail(uniEmail)
                .content(null)
                .profileUrl(null)
                .belong(null)
                .status(status)
                .build();
    }
}
