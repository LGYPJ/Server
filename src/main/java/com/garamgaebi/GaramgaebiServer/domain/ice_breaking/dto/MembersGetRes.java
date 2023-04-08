package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembersGetRes {
    private Long memberIdx;
    private String nickname;
    private String profileUrl;
    private Long currentMemberIdx;

    @Builder
    public MembersGetRes(Long memberIdx, String nickname, String profileUrl, Long currentMemberIdx) {
        this.memberIdx = memberIdx;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.currentMemberIdx = currentMemberIdx;
    }
}