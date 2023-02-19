package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.Data;

@Data
public class MemberLoginReq {
    private String socialEmail;
    private String fcmToken;
}
