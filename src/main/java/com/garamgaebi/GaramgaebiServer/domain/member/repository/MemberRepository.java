package com.garamgaebi.GaramgaebiServer.domain.member.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.nickname = :nickname")
    Optional<Member> findByNickname(@Param("nickname") String nickname);
}
