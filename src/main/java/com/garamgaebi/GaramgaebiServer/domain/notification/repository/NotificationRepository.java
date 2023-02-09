package com.garamgaebi.GaramgaebiServer.domain.notification.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.MemberNotification;
import com.garamgaebi.GaramgaebiServer.domain.entity.Notification;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


}
