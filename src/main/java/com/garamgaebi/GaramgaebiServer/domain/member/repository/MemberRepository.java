package com.garamgaebi.GaramgaebiServer.domain.member.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialEmail(String socialEmail);

    Optional<Member> findByUniEmail(String uniEmail);

    List<Member> findByStatus(MemberStatus memberStatus);

    @Query("select memberIdx from Member order by memberIdx desc limit 1")
    Long findLastIdx();
}
