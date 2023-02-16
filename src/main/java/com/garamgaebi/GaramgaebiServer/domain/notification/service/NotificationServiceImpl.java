package com.garamgaebi.GaramgaebiServer.domain.notification.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberNotification;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationResDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.repository.MemberNotificationRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.repository.NotificationRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final MemberNotificationRepository memberNotificationRepository;

    @Override
    @Transactional
    public GetNotificationResDto getMemberNotificationList(Long memberIdx, Long lastNotificationIdx) {

        Optional<Member> member = memberRepository.findById(memberIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        Slice<MemberNotification> memberNotifications = new SliceImpl<MemberNotification>(new ArrayList<>());

        if(lastNotificationIdx == null) {
            memberNotifications = memberNotificationRepository.findByMemberOrderByIdxDesc(member.get(), PageRequest.of(0, 10));
        }
        else {
            Optional<MemberNotification> lastMemberNotification = memberNotificationRepository.findById(lastNotificationIdx);

            if(lastMemberNotification.isEmpty()) {
                log.info("NOTIFICATION NOT EXIST : {}", lastNotificationIdx);
                throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR);
            }

            memberNotifications = memberNotificationRepository.findByIdxLessThanAndMemberOrderByIdxDesc(lastNotificationIdx, member.get(), PageRequest.of(0, 10));
        }

        List<GetNotificationDto> getNotificationDtos = new ArrayList<GetNotificationDto>();


        for(MemberNotification memberNotification : memberNotifications.getContent()) {
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

        return new GetNotificationResDto(getNotificationDtos, memberNotifications.hasNext());
    }

    @Override
    @Transactional
    public Boolean isMemberNotificationExist(Long memberIdx) {
        Optional<Member> member = memberRepository.findById(memberIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        return memberNotificationRepository.existsByMemberAndIsReadFalse(member.get());
    }
}
