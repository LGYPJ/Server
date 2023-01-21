package com.garamgaebi.GaramgaebiServer.domain.member.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByNickname(String nickname);
}
