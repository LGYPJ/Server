package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLogoutRes {
    private String memberInfo;

    @Builder
    public MemberLogoutRes(String memberInfo) {
        this.memberInfo = memberInfo;
    }
}
