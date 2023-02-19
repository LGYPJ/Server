package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.Data;

@Data
public class VerifyEmailReq {
    private String email;
    private String key;
}
