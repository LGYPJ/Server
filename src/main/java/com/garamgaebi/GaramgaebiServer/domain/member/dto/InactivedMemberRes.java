package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class InactivedMemberRes {
    private boolean inactive_success;
}
