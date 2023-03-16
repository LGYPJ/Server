package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.global.security.dto.TokenInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRefreshRes {
    private TokenInfo tokenInfo;
    private Member member;

    @Builder
    public TokenRefreshRes(TokenInfo tokenInfo, Member identifier) {
        this.tokenInfo = tokenInfo;
        this.member = identifier;
    }
}
