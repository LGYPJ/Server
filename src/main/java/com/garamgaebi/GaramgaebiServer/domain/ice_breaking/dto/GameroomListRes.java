package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameroomListRes {
    private Long programGameRoomIdx;

    private Long programIdx;

    private String roomId;

    private boolean isStarted;

    @Builder
    public GameroomListRes(Long programGameRoomIdx, Long programIdx, String roomId, boolean isStarted) {
        this.programGameRoomIdx = programGameRoomIdx;
        this.programIdx = programIdx;
        this.roomId = roomId;
        this.isStarted = isStarted;
    }
}
