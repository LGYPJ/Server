package com.garamgaebi.GaramgaebiServer.domain.profile.dto.reqeust;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateProfileReq {
    private Long memberIdx;
    private String nickname;
    private String belong;
    private String profileEmail;
    private String content;
}