package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.repository.NotificationRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
//@Async("applyThreadPoolExecutor")
@Transactional
@RequiredArgsConstructor
public class ApplyEventListenerImpl implements ApplyEventListener {

    private final NotificationSender notificationSender;
    private final NotificationRepository notificationRepository;

    @Override
    @EventListener
    public void handleApplyEvent(Apply apply) {

        Member member = apply.getMember();

        // 신청한 멤버 토큰 가져와서 저장
        String fcmToken = new String();

        // 알림 메세지 내용 가공
        NotificationDto notificationDto = new NotificationDto(
                NotificationType.모아보기,
                apply.getProgram().getTitle() + " 신청이 완료되었어요",
                apply.getProgram().getIdx(),
                apply.getProgram().getProgramType()
        );

        // 알림 발송
        if (!fcmToken.isBlank()) {
            notificationSender.sendByToken(fcmToken, notificationDto);
        }

        // 알림 엔티티 insert
        Notification notification = new Notification();
        notification.builder(notificationDto);
        notificationRepository.save(notification);

        // MemberNotification 추가
        MemberNotification memberNotification = new MemberNotification();
        memberNotification.setMember(member);
        memberNotification.setNotification(notification);

        // 리스트 저장
        notificationRepository.save(notification);
    }

    @Override
    @EventListener
    public void handleApplyCancelEvent(Apply apply) {
        Member member = apply.getMember();

        // 신청한 멤버 토큰 가져와서 저장
        String fcmToken = new String();

        // 알림 메세지 내용 가공
        NotificationDto notificationDto = new NotificationDto(
                NotificationType.모아보기,
                apply.getProgram().getTitle() + " 신청 취소가 완료되었어요",
                apply.getProgram().getIdx(),
                apply.getProgram().getProgramType()
        );

        // 알림 발송
        if(!fcmToken.isBlank()) {
            notificationSender.sendByToken(fcmToken, notificationDto);
        }

        // 알림 엔티티 insert
        Notification notification = new Notification();
        notification.builder(notificationDto);
        notificationRepository.save(notification);

        // MemberNotification 추가
        MemberNotification memberNotification = new MemberNotification();
        memberNotification.setMember(member);
        memberNotification.setNotification(notification);

        // 리스트 저장
        notificationRepository.save(notification);
    }

    @Override
    @EventListener
    public void handleRefundEvent(List<Apply> applyList) {
        // 관리자 페이지 구현 나오면 결정
    }
}
