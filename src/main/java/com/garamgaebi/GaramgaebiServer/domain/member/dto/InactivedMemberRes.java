package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InactivedMemberRes {
    private boolean inactive_success;

    @Builder
    public InactivedMemberRes(boolean inactive_success) {
        this.inactive_success = inactive_success;
    }
}
