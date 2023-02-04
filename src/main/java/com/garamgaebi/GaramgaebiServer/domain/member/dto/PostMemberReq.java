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
//    private String content;
//    private String profileUrl;
//
//    private String belong;
    private MemberStatus status;

    private String password;

    @Builder
    public PostMemberReq(String nickname,
                         String profileEmail,
                         String socialEmail,
                         String uniEmail,
                         MemberStatus status,
                         String password) {
        this.nickname = nickname;
        this.profileEmail = profileEmail;
        this.socialEmail = socialEmail;
        this.uniEmail = uniEmail;
        this.status = status;
        this.password = password;
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
                .password(password)
                .build();
    }
}
