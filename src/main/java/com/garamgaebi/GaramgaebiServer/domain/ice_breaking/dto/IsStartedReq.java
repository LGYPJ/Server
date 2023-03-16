package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsStartedReq {
    private String roomId;
}
