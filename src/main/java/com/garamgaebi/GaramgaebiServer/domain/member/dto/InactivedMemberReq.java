package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.MemberQuit;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberQuitStatus;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InactivedMemberReq {
    private Long memberIdx;
    private String content;
    private MemberQuitStatus category;

    @Builder
    public InactivedMemberReq(Long memberIdx,
                              String content,
                              MemberQuitStatus category) {
        this.memberIdx = memberIdx;
        this.content = content;
        this.category = category;
    }

    public MemberQuit toEntity() {
        return MemberQuit.builder()
                .memberIdx(memberIdx)
                .content(content)
                .category(category)
                .build();
    }
}
