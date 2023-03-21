package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMemberRes {
    private Long memberIdx;

    @Builder
    public PostMemberRes(Long memberIdx) {
        this.memberIdx = memberIdx;
    }
}
