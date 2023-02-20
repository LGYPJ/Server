package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerifyEmailReq {
    private String email;
    private String key;
}
