package com.garamgaebi.GaramgaebiServer.domain.notification.service;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.Notification;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationResDto;

import java.util.List;

public interface NotificationService {
    public GetNotificationResDto getMemberNotificationList(Long memberIdx, Long lastNotificationIdx);

    public Boolean isMemberNotificationExist(Long memberIdx);

    public void addNotification(Notification notification, List<Member> members);

    public void addNotification(Notification notification, Member member);
}
