package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class EmailRes {
    private String message;

    @Builder
    public EmailRes(String message) {
        this.message = message;
    }
}
