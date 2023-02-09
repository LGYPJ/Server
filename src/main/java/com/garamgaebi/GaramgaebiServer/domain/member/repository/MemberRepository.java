package com.garamgaebi.GaramgaebiServer.domain.member.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUniEmail(String uniEmail);

    List<Member> findByStatus(MemberStatus memberStatus);
}
