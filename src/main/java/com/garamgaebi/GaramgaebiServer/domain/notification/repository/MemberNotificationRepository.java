package com.garamgaebi.GaramgaebiServer.domain.notification.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberNotification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {
    @Query("select m from MemberNotification m " +
            "where m.member = :member and m.notification not in (select tmp from Notification tmp where tmp.status = 'DELETE') order by m.createdAt desc")
    public List<MemberNotification> findMemberNotificationList(@Param("member") Member member, Pageable pageable);
}
