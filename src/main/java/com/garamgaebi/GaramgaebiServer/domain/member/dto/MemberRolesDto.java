package com.garamgaebi.GaramgaebiServer.domain.member.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.MemberRoles;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberRolesDto {
    private Long memberIdx;
    private String roles;

    public MemberRoles toEntity() {
        return MemberRoles.builder()
                .memberIdx(memberIdx)
                .roles(roles)
                .build();
    }
}
