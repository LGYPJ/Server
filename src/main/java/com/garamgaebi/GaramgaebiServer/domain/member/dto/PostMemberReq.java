package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.vo.MemberStatus;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMemberReq {
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{1,8}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String nickname;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String profileEmail;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String socialEmail;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String uniEmail;
    private MemberStatus status;

    public Member toEntity() {벼
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
