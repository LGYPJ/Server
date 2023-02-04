package com.garamgaebi.GaramgaebiServer.domain.notification.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberNotification;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.repository.MemberNotificationRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.repository.NotificationRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final MemberNotificationRepository memberNotificationRepository;

    @Override
    @Transactional
    public List<GetNotificationDto> getMemberNotificationList(Long memberIdx, Pageable pageable) {

        Optional<Member> member = memberRepository.findById(memberIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        List<MemberNotification> memberNotifications = memberNotificationRepository.findMemberNotificationList(member.get(), pageable);
        List<GetNotificationDto> getNotificationDtos = new ArrayList<GetNotificationDto>();

        for(MemberNotification memberNotification : memberNotifications) {
            getNotificationDtos.add(GetNotificationDto.builder()
                            .notificationIdx(memberNotification.getIdx())
                                .content(memberNotification.getNotification().getContent())
                            .notificationType(memberNotification.getNotification().getNotificationType())
                            .resourceIdx(memberNotification.getNotification().getResourceIdx())
                            .resourceType(memberNotification.getNotification().getResourceType())
                            .isRead(memberNotification.getIsRead()).build());

            memberNotification.read();
            memberNotificationRepository.save(memberNotification);
        }

        return getNotificationDtos;
    }
}
