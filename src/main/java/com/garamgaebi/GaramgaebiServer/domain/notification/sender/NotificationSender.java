package com.garamgaebi.GaramgaebiServer.domain.notification.sender;

import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;

import java.util.List;

public interface NotificationSender {
    public void sendByTokenList(List<String> tokenList, NotificationDto notificationDto);
    public void sendByToken(String tokenList, NotificationDto notificationDto);
}
