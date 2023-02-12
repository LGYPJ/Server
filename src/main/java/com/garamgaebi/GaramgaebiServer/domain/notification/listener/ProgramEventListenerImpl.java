package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.notification.NotificationType;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.DeadlineEvent;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.ProgramOpenEvent;
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
//@Async("programThreadPoolExecutor")
@RequiredArgsConstructor
public class ProgramEventListenerImpl implements ProgramEventListener {

    private final NotificationSender notificationSender;
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Override
    @Async
    @EventListener
    @Transactional
    public void handleProgramOpenEvent(ProgramOpenEvent seminarOpenEvent) {

        Program program = seminarOpenEvent.getProgram();

        // 로그인 된 멤버 리스트 가져오기
        List<Member> memberList = memberRepository.findByStatus(MemberStatus.ACTIVE);

        // 전체 멤버에게 알림 -> 전체 멤버 토큰 받아오기
        List<String> fcmTokenList = new ArrayList<>();

        // 알림 메세지 내용 가공

        String message;
        if(program.getProgramType() == ProgramType.SEMINAR) {
            message = "새로운 세미나가 오픈되었어요";
        }
        else if(program.getProgramType() == ProgramType.NETWORKING) {
             message = "새로운 네트워킹이 오픈되었어요";
        }
        else {
            // 잘못된 프로그램 타입으로 알림 전송 실패 log
            return;
        }

        // 알림 엔티티 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.COLLECTIONS)
                .content(message)
                .resourceIdx(program.getIdx())
                .resourceType(program.getProgramType())
                .build();

        // 알림 벌크 저장
        for(Member member : memberList) {
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

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            notificationSender.sendByTokenList(fcmTokenList, notificationDto);
        }

    }

    @Override
    @Async
    @EventListener
    @Transactional
    public void handleProgramDeadlineEvent(DeadlineEvent deadlineEvent) {

        Program program = deadlineEvent.getProgram();

        // 전체 멤버 리스트 가져오기
        List<Member> memberList = memberRepository.findByStatus(MemberStatus.ACTIVE);

        // 푸시 알림 허용한 멤버의 fcm 토큰 리스트
        List<String> fcmTokenList = new ArrayList<>();

        // 알림 엔티티에 메세지 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.SOON_CLOSE)
                .content(program.getTitle() + " 마감이 임박했어요")
                .resourceIdx(program.getIdx())
                .resourceType(program.getProgramType())
                .build();

        // 알림 벌크 저장
        for(Member member : memberList) {
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

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            notificationSender.sendByTokenList(fcmTokenList, notificationDto);
        }

    }
}
