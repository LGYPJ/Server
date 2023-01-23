package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberStatus;
import lombok.*;

import java.sql.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMemberReq {
    private String nickname;
    private String profileEmail;
    private String socialEmail;
    private String uniEmail;
    private String content;
    private String profileUrl;
    private MemberStatus status;

    @Builder
    public PostMemberReq(String nickname,
                         String profileEmail,
                         String socialEmail,
                         String uniEmail,
                         String content,
                         String profileUrl,
                         MemberStatus status) {
        this.nickname = nickname;
        this.profileEmail = profileEmail;
        this.socialEmail = socialEmail;
        this.uniEmail = uniEmail;
        this.content = content;
        this.profileUrl = profileUrl;
        this.status = status;
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .profileEmail(profileEmail)
                .socialEmail(socialEmail)
                .uniEmail(uniEmail)
                .content(content)
                .profileUrl(profileUrl)
                .status(status)
                .build();
    }
}
