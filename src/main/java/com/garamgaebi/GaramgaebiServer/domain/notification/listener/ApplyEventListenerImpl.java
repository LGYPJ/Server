package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.notification.repository.NotificationRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Async("apply")
@Transactional
@RequiredArgsConstructor
public class ApplyEventListenerImpl implements ApplyEventListener {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @Override
    public void handleApplyEvent() {

    }

    @Override
    public void handleApplyCancelEvent() {

    }

    @Override
    public void handleRefundEvent() {

    }
}
