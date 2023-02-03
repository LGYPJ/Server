package com.garamgaebi.GaramgaebiServer.domain.notification.service;

import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationDto;

import java.util.List;

public interface NotificationService {
    public List<GetNotificationDto> getMemberNotificationList();

}
