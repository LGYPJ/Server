package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.notification.NotificationType;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.ApplyCancelEvent;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.ApplyEvent;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.RefundEvent;
import com.garamgaebi.GaramgaebiServer.domain.notification.repository.NotificationRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
//@Async("applyThreadPoolExecutor")
@RequiredArgsConstructor
public class ApplyEventListenerImpl implements ApplyEventListener {

    private final NotificationSender notificationSender;
    private final NotificationRepository notificationRepository;

    private final MemberRepository memberRepository;

    @Override
    @Async
    @Transactional
    @EventListener
    public void handleApplyEvent(ApplyEvent applyEvent) {

        Apply apply = applyEvent.getApply();

        Optional<Member> memberWrapper = memberRepository.findById(apply.getMember().getMemberIdx());
        if(memberWrapper.isEmpty()) {
            // 없는 멤버 로그
            return;
        }

        Member member = memberWrapper.get();

        // 신청한 멤버 토큰 가져와서 저장
        String fcmToken = new String();

        // 알림 메세지 내용 가공
        // 알림 엔티티 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.APPLY_COMPLETE)
                .content(apply.getProgram().getTitle() + " 신청이 완료되었어요")
                .resourceIdx(apply.getProgram().getIdx())
                .resourceType(apply.getProgram().getProgramType())
                .build();


        // MemberNotification 추가
        MemberNotification memberNotification = new MemberNotification();
        memberNotification.setMember(member);
        memberNotification.setNotification(notification);

        // 리스트 저장
        notificationRepository.save(notification);

        NotificationDto notificationDto = new NotificationDto(
                notification.getNotificationType(),
                notification.getContent(),
                notification.getResourceIdx(),
                notification.getResourceType()
        );

        // 알림 발송
        if (!fcmToken.isBlank()) {
            notificationSender.sendByToken(fcmToken, notificationDto);
        }

    }

    @Override
    @Async
    @Transactional
    @EventListener
    public void handleApplyCancelEvent(ApplyCancelEvent applyCancelEvent) {

        Apply apply = applyCancelEvent.getApply();

        Optional<Member> memberWrapper = memberRepository.findById(apply.getMember().getMemberIdx());
        if(memberWrapper.isEmpty()) {
            // 없는 멤버 로그
            return;
        }

        Member member = memberWrapper.get();

        // 신청한 멤버 토큰 가져와서 저장
        String fcmToken = new String();

        // 알림 메세지 내용 가공
        // 알림 엔티티 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.APPLY_CANCEL_COMPLETE)
                .content(apply.getProgram().getTitle() + " 신청이 취소 되었어요")
                .resourceIdx(apply.getProgram().getIdx())
                .resourceType(apply.getProgram().getProgramType())
                .build();

        // MemberNotification 추가
        MemberNotification memberNotification = new MemberNotification();
        memberNotification.setMember(member);
        memberNotification.setNotification(notification);

        // 리스트 저장
        notificationRepository.save(notification);

        NotificationDto notificationDto = new NotificationDto(
                notification.getNotificationType(),
                notification.getContent(),
                notification.getResourceIdx(),
                notification.getResourceType()
        );

        // 알림 발송
        if(!fcmToken.isBlank()) {
            notificationSender.sendByToken(fcmToken, notificationDto);
        }

    }

    @Override
    @Async
    @TransactionalEventListener
    public void handleRefundEvent(RefundEvent refundEvent) {
        // 관리자 페이지 구현 나오면 결정
    }
}
