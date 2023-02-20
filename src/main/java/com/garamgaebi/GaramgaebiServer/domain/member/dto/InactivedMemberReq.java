package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import com.garamgaebi.GaramgaebiServer.domain.profile.entity.MemberQuit;
import com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.MemberQuitStatus;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InactivedMemberReq {
    private Long memberIdx;
    private String content;
    private MemberQuitStatus category;

    public MemberQuit toEntity() {
        return MemberQuit.builder()
                .memberIdx(memberIdx)
                .content(content)
                .category(category)
                .build();
    }
}
