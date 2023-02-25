package com.garamgaebi.GaramgaebiServer.domain.profile.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetProfileRes {
    private Long memberIdx;
    private String nickName;
    private String profileEmail;
    private String belong;
    private String content;
    private String profileUrl;
}
