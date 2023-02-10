package com.garamgaebi.GaramgaebiServer.domain.notification.service;

import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface NotificationService {
    public GetNotificationResDto getMemberNotificationList(Long memberIdx, Long lastNotificationIdx);

    public Boolean isMemberNotificationExist(Long memberIdx);
}
