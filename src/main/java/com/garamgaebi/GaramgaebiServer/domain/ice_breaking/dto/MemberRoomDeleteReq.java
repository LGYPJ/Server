package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberRoomDeleteReq {
    private String roomId;
    private Long nextMemberIdx;

    public MemberRoomDeleteReq(String roomId, Long nextMemberIdx) {
        this.roomId = roomId;
        this.nextMemberIdx = nextMemberIdx;
    }
}