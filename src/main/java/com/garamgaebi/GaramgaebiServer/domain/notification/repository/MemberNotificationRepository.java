package com.garamgaebi.GaramgaebiServer.domain.notification.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {

    public Slice<MemberNotification> findByMemberOrderByIdxDesc(@Param("member") Member member, Pageable pageable);

    public Slice<MemberNotification> findByIdxLessThanAndMemberOrderByIdxDesc(@Param("idx")Long lastNotificationIdx, @Param("member") Member member, Pageable pageable);

    public Boolean existsByMemberAndIsReadFalse(Member member);
}
