package com.garamgaebi.GaramgaebiServer.domain.email.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendEmailReq {
    private String email;
}
