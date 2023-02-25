package com.garamgaebi.GaramgaebiServer.domain.member.repository;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.MemberRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRolesRepository extends JpaRepository<MemberRoles, Long> {
    Optional<MemberRoles> findByMemberIdx(Long memberIdx);
}
