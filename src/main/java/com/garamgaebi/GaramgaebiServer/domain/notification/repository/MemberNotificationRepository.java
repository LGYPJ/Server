package com.garamgaebi.GaramgaebiServer.domain.notification.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberNotification;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {

    public List<MemberNotification> findByMember(@Param("member") Member member, Pageable pageable);

    public Boolean existsByMemberAndIsReadFalse(Member member);
}
