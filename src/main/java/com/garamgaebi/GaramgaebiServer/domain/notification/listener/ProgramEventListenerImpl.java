package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.notification.repository.NotificationRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Async("program")
@Transactional
@RequiredArgsConstructor
public class ProgramEventListenerImpl implements ProgramEventListener {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @Override
    public void handleSeminarOpenEvent() {

    }

    @Override
    public void handleNetworkingOpenEvent() {

    }

    @Override
    public void handleProgramDeadlineEvent() {

    }
}
