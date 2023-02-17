package com.garamgaebi.GaramgaebiServer.domain.notification.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.Notification;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationService {
    public GetNotificationResDto getMemberNotificationList(Long memberIdx, Long lastNotificationIdx);

    public Boolean isMemberNotificationExist(Long memberIdx);

    public void addNotification(Notification notification, List<Member> members);

    public void addNotification(Notification notification, Member member);
}
