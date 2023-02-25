package com.garamgaebi.GaramgaebiServer.domain.member.repository;

import com.garamgaebi.GaramgaebiServer.domain.profile.entity.MemberQuit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberQuitRepository extends JpaRepository<MemberQuit, Long> {
}
