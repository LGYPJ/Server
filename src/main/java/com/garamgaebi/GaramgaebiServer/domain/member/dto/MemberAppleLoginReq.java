package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAppleLoginReq {
    private String idToken;
    private String fcmToken;
}
