package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLogoutReq {
    private String accessToken;
    private String refreshToken;
    private String fcmToken;
}
