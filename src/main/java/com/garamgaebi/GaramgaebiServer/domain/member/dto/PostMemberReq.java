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

    @Builder
    public PostMemberReq(String nickname,
                         String profileEmail,
                         String socialEmail,
                         String uniEmail,
                         MemberStatus status) {
        this.nickname = nickname;
        this.profileEmail = profileEmail;
        this.socialEmail = socialEmail;
        this.uniEmail = uniEmail;
        this.status = status;
    }

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
