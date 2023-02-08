package com.garamgaebi.GaramgaebiServer.domain.notification.service;

import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    public List<GetNotificationDto> getMemberNotificationList(Long memberIdx, Pageable pageable);

    public Boolean isMemberNotificationExist(Long memberIdx);
}
