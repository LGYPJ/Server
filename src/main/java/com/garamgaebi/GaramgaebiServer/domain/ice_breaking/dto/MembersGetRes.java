package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MembersGetRes {
    private Long memberIdx;
    private String nickname;

    private String profileUrl;
}