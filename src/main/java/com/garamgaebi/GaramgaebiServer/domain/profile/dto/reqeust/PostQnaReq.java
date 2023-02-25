package com.garamgaebi.GaramgaebiServer.domain.profile.dto.reqeust;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostQnaReq {
    private Long memberIdx;
    private String email;
    private String category;
    private String content;

}
