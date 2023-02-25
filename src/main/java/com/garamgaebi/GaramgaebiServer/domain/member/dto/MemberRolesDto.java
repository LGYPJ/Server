package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.MemberRoles;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRolesDto {
    private Long memberIdx;
    private String roles;

    @Builder
    public MemberRolesDto(Long memberIdx, String roles) {
        this.memberIdx = memberIdx;
        this.roles = roles;
    }

    public MemberRoles toEntity() {
        return MemberRoles.builder()
                .memberIdx(memberIdx)
                .roles(roles)
                .build();
    }
}
