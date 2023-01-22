package com.garamgaebi.GaramgaebiServer.domain.profile.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateProfileReq {
    private Long memberIdx;
    private String nickName;
    private String profileEmail;
    private String content;
}