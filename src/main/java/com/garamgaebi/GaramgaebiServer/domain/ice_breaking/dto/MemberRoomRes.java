package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRoomRes {
    private String message;
    private int currentImgIdx;

    @Builder
    public MemberRoomRes(String message, int currentImgIdx) {
        this.message = message;
        this.currentImgIdx = currentImgIdx;
    }
}
