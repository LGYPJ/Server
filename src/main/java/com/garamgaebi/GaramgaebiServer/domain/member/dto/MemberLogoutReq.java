package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLogoutReq {
    private String accessToken;
    private String refreshToken;
    private String fcmToken;
}
