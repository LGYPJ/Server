package com.garamgaebi.GaramgaebiServer.domain.notification.repository;

import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    public List<Notification> findByResourceIdx(Long resourceIdx);

}
