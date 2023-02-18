package com.garamgaebi.GaramgaebiServer.domain.notification.sender;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.Notification;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;

import java.util.List;

public interface NotificationSender {
    public void sendNotification(Notification notification, List<Member> members);
    public void sendNotification(Notification notification, Member member);
}
