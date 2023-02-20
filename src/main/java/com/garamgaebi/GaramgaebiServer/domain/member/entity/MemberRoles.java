package com.garamgaebi.GaramgaebiServer.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "Member_roles")
public class MemberRoles {
    @Id
    @Column(name = "member_roles_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberRolesIdx;

    @Column(name = "member_member_idx", nullable = false)
    private Long memberIdx;

    @Column(nullable = false)
    private String roles;

    @Builder
    public MemberRoles(Long memberIdx, String roles) {
        this.memberIdx = memberIdx;
        this.roles = roles;
    }

    public void inactivedMember() {
        this.roles = "INACTIVE";
    }
}
