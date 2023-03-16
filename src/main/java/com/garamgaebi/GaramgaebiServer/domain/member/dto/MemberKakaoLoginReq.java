package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberKakaoLoginReq {
    private String accessToken;
    private String fcmToken;
}
