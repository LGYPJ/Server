package com.garamgaebi.GaramgaebiServer.domain.profile.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSNSReq {
    private Long memberIdx;
    private String address;
}
