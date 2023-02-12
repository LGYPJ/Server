package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.apply.ApplyRepository;
import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.notification.NotificationType;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.*;
import com.garamgaebi.GaramgaebiServer.domain.notification.repository.NotificationRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.sender.NotificationSender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
//@Async("applyThreadPoolExecutor")
@RequiredArgsConstructor
public class ApplyEventListenerImpl implements ApplyEventListener {

    private final NotificationSender notificationSender;
    private final NotificationRepository notificationRepository;

    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;

    @Override
    @Async
    @Transactional
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

        // 해당 유저의 fcm Token list로 알림 전달
        List<String> fcmTokenList = new ArrayList<>();

        // fcm 토큰 리스트 추가
        for(MemberFcm memberFcm : member.getMemberFcms()) {
            fcmTokenList.add(memberFcm.getFcmToken());
        }

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            notificationSender.sendByTokenList(fcmTokenList, notificationDto);
        }

    }

    @Override
    @Async
    @Transactional
    @EventListener
    public void handleApplyCancelEvent(ApplyCancelEvent applyCancelEvent) {

        Apply apply = applyCancelEvent.getApply();

        Member member = apply.getMember();

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

        // 해당 유저의 fcm Token list로 알림 전달
        List<String> fcmTokenList = new ArrayList<>();

        // fcm 토큰 리스트 추가
        for(MemberFcm memberFcm : member.getMemberFcms()) {
            fcmTokenList.add(memberFcm.getFcmToken());
        }

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            notificationSender.sendByTokenList(fcmTokenList, notificationDto);
        }
    }

    @Override
    @Async
    @Transactional
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

        // MemberNotification 추가
        for(Member member : members) {
            MemberNotification memberNotification = new MemberNotification();
            memberNotification.setMember(member);
            memberNotification.setNotification(notification);
        }

        // 리스트 저장
        notificationRepository.save(notification);

        NotificationDto notificationDto = new NotificationDto(
                notification.getNotificationType(),
                notification.getContent(),
                notification.getResourceIdx(),
                notification.getResourceType()
        );

        // 해당 유저의 fcm Token list로 알림 전달
        List<String> fcmTokenList = new ArrayList<>();

        // fcm 토큰 리스트 추가
        for(Member member : members) {
            for (MemberFcm memberFcm : member.getMemberFcms()) {
                fcmTokenList.add(memberFcm.getFcmToken());
            }
        }

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            notificationSender.sendByTokenList(fcmTokenList, notificationDto);
        }
    }

    @Override
    @Async
    @Transactional
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

        // MemberNotification 추가
        for(Member member : members) {
            MemberNotification memberNotification = new MemberNotification();
            memberNotification.setMember(member);
            memberNotification.setNotification(notification);
        }

        // 리스트 저장
        notificationRepository.save(notification);

        NotificationDto notificationDto = new NotificationDto(
                notification.getNotificationType(),
                notification.getContent(),
                notification.getResourceIdx(),
                notification.getResourceType()
        );

        // 해당 유저의 fcm Token list로 알림 전달
        List<String> fcmTokenList = new ArrayList<>();

        // fcm 토큰 리스트 추가
        for(Member member : members) {
            for (MemberFcm memberFcm : member.getMemberFcms()) {
                fcmTokenList.add(memberFcm.getFcmToken());
            }
        }

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            notificationSender.sendByTokenList(fcmTokenList, notificationDto);
        }
    }

    @Override
    @Async
    @Transactional
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
                .notificationType(NotificationType.APPLY_CONFIRM)
                .content("참가비 미입금으로 " + program.getTitle() + " 신청이 취소되었어요")
                .resourceIdx(program.getIdx())
                .resourceType(program.getProgramType())
                .build();

        // MemberNotification 추가
        for(Member member : members) {
            MemberNotification memberNotification = new MemberNotification();
            memberNotification.setMember(member);
            memberNotification.setNotification(notification);
        }

        // 리스트 저장
        notificationRepository.save(notification);

        NotificationDto notificationDto = new NotificationDto(
                notification.getNotificationType(),
                notification.getContent(),
                notification.getResourceIdx(),
                notification.getResourceType()
        );

        // 해당 유저의 fcm Token list로 알림 전달
        List<String> fcmTokenList = new ArrayList<>();

        // fcm 토큰 리스트 추가
        for(Member member : members) {
            for (MemberFcm memberFcm : member.getMemberFcms()) {
                fcmTokenList.add(memberFcm.getFcmToken());
            }
        }

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            notificationSender.sendByTokenList(fcmTokenList, notificationDto);
        }
    }

}
