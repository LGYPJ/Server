package com.garamgaebi.GaramgaebiServer.domain.profile.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfilesRes {
    private Long memberIdx;
    private String nickName;
}
