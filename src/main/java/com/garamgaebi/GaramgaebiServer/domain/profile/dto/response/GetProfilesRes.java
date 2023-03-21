package com.garamgaebi.GaramgaebiServer.domain.profile.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class GetProfilesRes {
    private Long memberIdx;
    private String nickName;
    private String belong;
    private String profileUrl;
}