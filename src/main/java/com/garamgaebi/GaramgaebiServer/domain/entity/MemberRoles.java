package com.garamgaebi.GaramgaebiServer.domain.entity;

import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
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

    public void inactivedMember() {
        this.roles = "INACTIVE";
    }
}
