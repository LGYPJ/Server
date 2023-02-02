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
@Async("program")
@Transactional
@RequiredArgsConstructor
public class ProgramEventListenerImpl implements ProgramEventListener {

    private final NotificationSender notificationSender;
    private final NotificationRepository notificationRepository;

    @Override
    @EventListener
    public void handleSeminarOpenEvent(Program program) {

        // 로그인 된 멤버 리스트 가져오기
        List<Member> memberList = new ArrayList<>();

        // 전체 멤버에게 알림 -> 전체 멤버 토큰 받아오기
        List<String> fcmTokenList = new ArrayList<>();

        // 알림 메세지 내용 가공
        NotificationDto notificationDto = new NotificationDto(
                NotificationType.모아보기,
                "새로운 세미나가 오픈되었어요",
                program.getIdx(),
                program.getProgramType()
        );

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            notificationSender.sendByTokenList(fcmTokenList, notificationDto);
        }

        // 알림 엔티티 insert
        Notification notification = new Notification();
        notification.builder(notificationDto);
        notificationRepository.save(notification);

        // 알림 벌크 저장
        for(Member member : memberList) {
            MemberNotification memberNotification = new MemberNotification();
            memberNotification.setMember(member);
            memberNotification.setNotification(notification);
        }

        // 리스트 저장
        notificationRepository.save(notification);
    }

    @Override
    @EventListener
    public void handleNetworkingOpenEvent(Program program) {

        // 로그인 된 멤버 리스트 가져오기
        List<Member> memberList = new ArrayList<>();

        // 전체 멤버에게 알림 -> 전체 멤버 토큰 받아오기
        List<String> fcmTokenList = new ArrayList<>();

        // 알림 메세지 내용 가공
        NotificationDto notificationDto = new NotificationDto(
                NotificationType.모아보기,
                "새로운 네트워킹이 오픈되었어요",
                program.getIdx(),
                program.getProgramType()
        );

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            notificationSender.sendByTokenList(fcmTokenList, notificationDto);
        }

        // 알림 엔티티 insert
        Notification notification = new Notification();
        notification.builder(notificationDto);
        notificationRepository.save(notification);

        // 알림 벌크 저장
        for(Member member : memberList) {
            MemberNotification memberNotification = new MemberNotification();
            memberNotification.setMember(member);
            memberNotification.setNotification(notification);
        }

        // 리스트 저장
        notificationRepository.save(notification);
    }

    @Override
    @EventListener
    public void handleProgramDeadlineEvent(Program program) {
        // 동적 스케줄링 알아보고 구현해보기
    }
}
