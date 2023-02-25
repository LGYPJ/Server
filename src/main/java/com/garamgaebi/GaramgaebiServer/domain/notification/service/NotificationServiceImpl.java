package com.garamgaebi.GaramgaebiServer.domain.notification.service;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.MemberNotification;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.Notification;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.vo.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.response.GetNotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.response.GetNotificationResDto;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final MemberRepository memberRepository;
    private final MemberNotificationRepository memberNotificationRepository;

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public GetNotificationResDto getMemberNotificationList(Long memberIdx, Long lastNotificationIdx) {

        Member member = validMember(memberIdx);

        Slice<MemberNotification> memberNotifications;

        if(lastNotificationIdx == null) {
            memberNotifications = memberNotificationRepository.findByMemberOrderByIdxDesc(member, PageRequest.of(0, 10));
        }
        else {
            memberNotificationRepository.findById(lastNotificationIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

            memberNotifications = memberNotificationRepository.findByIdxLessThanAndMemberOrderByIdxDesc(lastNotificationIdx, member, PageRequest.of(0, 10));
        }

        List<GetNotificationDto> getNotificationDtos = new ArrayList<GetNotificationDto>();

        for(MemberNotification memberNotification : memberNotifications) {
            getNotificationDtos.add(memberNotification.toGetNotificationDto());
            memberNotification.read();
        }
        memberNotificationRepository.saveAll(memberNotifications);

        return GetNotificationResDto.builder()
                .result(getNotificationDtos)
                .hasNext(memberNotifications.hasNext())
                .build();

    }


    @Override
    @Transactional
    public Boolean isMemberNotificationExist(Long memberIdx) {
        Member member = validMember(memberIdx);

        return memberNotificationRepository.existsByMemberAndIsReadFalse(member);
    }

    @Override
    @Transactional
    public void addNotification(Notification notification, List<Member> members) {

        // MemberNotification 추가
        members.stream().forEach(member -> notification.addMemberNotifications(MemberNotification.builder()
                        .member(member)
                        .notification(notification)
                .build()));

        // 리스트 저장
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void addNotification(Notification notification, Member member) {
        notificationRepository.save(notification);

        notification.addMemberNotifications(MemberNotification.builder()
                .member(member)
                .notification(notification)
                .build());

        // 리스트 저장
        notificationRepository.save(notification);
    }

    private Member validMember(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

        if(member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        return member;
    }
}
