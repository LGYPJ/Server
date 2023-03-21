package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import com.garamgaebi.GaramgaebiServer.global.security.dto.TokenInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRes {
    private TokenInfo tokenInfo;
    private String uniEmail;
    private String nickname;

    @Builder
    public LoginRes(TokenInfo tokenInfo, String uniEmail, String nickname) {
        this.tokenInfo = tokenInfo;
        this.uniEmail = uniEmail;
        this.nickname = nickname;
    }
}
