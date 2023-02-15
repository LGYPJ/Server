package com.garamgaebi.GaramgaebiServer.domain.member.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.MemberRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface MemberRolesRepository extends JpaRepository<MemberRoles, Long> {
    Optional<MemberRoles> findByMemberIdx(Long memberIdx);
}
