package com.garamgaebi.GaramgaebiServer.domain.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialUserInfoDto {
    private String nickname;
    private String email;

    public SocialUserInfoDto(Long id, String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
