package com.garamgaebi.GaramgaebiServer.domain.notification.service;

import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;

import java.util.List;

public interface NotificationService{
    public void sendByTokenList(List<String> tokenList, NotificationDto notificationDto);
}
