package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo.NotificationType;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.Notification;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.*;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.global.util.firebase.NotificationSender;
import com.garamgaebi.GaramgaebiServer.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
//@Async("applyThreadPoolExecutor")
@RequiredArgsConstructor
public class ApplyEventListenerImpl implements ApplyEventListener {

    private final NotificationSender notificationSender;
    private final NotificationService notificationService;


    @Override
    @Async
    @EventListener
    public void handleApplyEvent(ApplyEvent applyEvent) {

        Apply apply = applyEvent.getApply();

        Member member = apply.getMember();

        // 알림 메세지 내용 가공
        // 알림 엔티티 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.APPLY_COMPLETE)
                .content(apply.getProgram().getTitle() + " 신청이 완료되었어요")
                .resourceIdx(apply.getProgram().getIdx())
                .resourceType(apply.getProgram().getProgramType())
                .build();

        // 알림 DB에 저장
        notificationService.addNotification(notification, member);

        // 알림 전송
        notificationSender.sendNotification(notification, member);

    }

    @Override
    @Async
    @EventListener
    public void handleApplyCancelEvent(ApplyCancelEvent applyCancelEvent) {

        Apply apply = applyCancelEvent.getApply();

        Member member = apply.getMember();

        // 알림 메세지 내용 가공
        // 알림 엔티티 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.APPLY_CANCEL_COMPLETE)
                .content(apply.getProgram().getTitle() + " 신청이 취소되었어요")
                .resourceIdx(apply.getProgram().getIdx())
                .resourceType(apply.getProgram().getProgramType())
                .build();

        // 알림 DB에 저장
        notificationService.addNotification(notification, member);

        // 알림 전송
        notificationSender.sendNotification(notification, member);
    }

    @Override
    @Async
    @EventListener
    public void handleRefundEvent(RefundEvent refundEvent) {
        List<Apply> applies = refundEvent.getApplies();

        List<Member> members = new ArrayList<Member>();

        Program program = refundEvent.getProgram();

        for(Apply apply : applies) {
            members.add(apply.getMember());
        }

        // 알림 메세지 내용 가공
        // 알림 엔티티 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.REFUND_COMPLETE)
                .content(program.getTitle() + " 환불이 완료되었어요")
                .resourceIdx(program.getIdx())
                .resourceType(program.getProgramType())
                .build();

        // 알림 DB에 저장
        notificationService.addNotification(notification, members);

        // 알림 전송
        notificationSender.sendNotification(notification, members);
    }

    @Override
    @Async
    @EventListener
    public void handleApplyConfirmEvent(ApplyConfirmEvent applyConfirmEvent) {
        List<Apply> applies = applyConfirmEvent.getApplies();

        List<Member> members = new ArrayList<Member>();

        Program program = applyConfirmEvent.getProgram();

        for(Apply apply : applies) {
            members.add(apply.getMember());
        }

        // 알림 메세지 내용 가공
        // 알림 엔티티 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.APPLY_CONFIRM)
                .content(program.getTitle() + " 신청이 확정되었어요")
                .resourceIdx(program.getIdx())
                .resourceType(program.getProgramType())
                .build();

        // 알림 DB에 저장
        notificationService.addNotification(notification, members);

        // 알림 전송
        notificationSender.sendNotification(notification, members);
    }

    @Override
    @Async
    @EventListener
    public void handleNonDepositCancelEvent(NonDepositCancelEvent nonDepositCancelEvent) {
        List<Apply> applies = nonDepositCancelEvent.getApplies();

        List<Member> members = new ArrayList<Member>();

        Program program = nonDepositCancelEvent.getProgram();

        for(Apply apply : applies) {
            members.add(apply.getMember());
        }

        // 알림 메세지 내용 가공
        // 알림 엔티티 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.NON_DEPOSIT_CANCEL)
                .content("참가비 미입금으로 " + program.getTitle() + " 신청이 취소되었어요")
                .resourceIdx(program.getIdx())
                .resourceType(program.getProgramType())
                .build();

        // 알림 DB에 저장
        notificationService.addNotification(notification, members);

        // 알림 전송
        notificationSender.sendNotification(notification, members);
    }


}
