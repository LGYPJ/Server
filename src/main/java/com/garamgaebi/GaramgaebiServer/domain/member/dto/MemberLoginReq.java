package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginReq {
    private String accessToken;
    private String fcmToken;
}
