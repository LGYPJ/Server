package com.garamgaebi.GaramgaebiServer.domain.profile.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetProfilesRes {
    private Long memberIdx;
    private String nickName;
    private String belong;
    private String profileUrl;
    private String group;
    private String detail;
}