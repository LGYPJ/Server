package com.garamgaebi.GaramgaebiServer.domain.notification.repository;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.MemberNotification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {

    public Slice<MemberNotification> findByMemberOrderByIdxDesc(@Param("member") Member member, Pageable pageable);

    public Slice<MemberNotification> findByIdxLessThanAndMemberOrderByIdxDesc(@Param("idx")Long lastNotificationIdx, @Param("member") Member member, Pageable pageable);

    public Boolean existsByMemberAndIsReadFalse(Member member);
}
