package com.garamgaebi.GaramgaebiServer.global.util.firebase;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.Notification;

import java.util.List;

public interface NotificationSender {
    public void sendNotification(Notification notification, List<Member> members);
    public void sendNotification(Notification notification, Member member);
}
