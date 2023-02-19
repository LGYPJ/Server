package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRoomReq {
    private String roomId;
    private Long memberIdx;
}
