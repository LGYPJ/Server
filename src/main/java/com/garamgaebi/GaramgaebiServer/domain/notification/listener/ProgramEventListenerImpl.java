package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.vo.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo.NotificationType;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.Notification;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.DeadlineEvent;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.ProgramOpenEvent;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.global.util.firebase.NotificationSender;
import com.garamgaebi.GaramgaebiServer.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProgramEventListenerImpl implements ProgramEventListener {

    private final NotificationSender notificationSender;
    private final NotificationService notificationService;
    private final MemberRepository memberRepository;

    @Override
    @Async
    @EventListener
    public void handleProgramOpenEvent(ProgramOpenEvent seminarOpenEvent) {

        Program program = seminarOpenEvent.getProgram();

        // 멤버 리스트 가져오기
        List<Member> members = memberRepository.findByStatus(MemberStatus.ACTIVE);

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
            log.error("FAIL SEND NOTIFICATION : UNKNOWN PROGRAM TYPE {}", program.getIdx());
            return;
        }

        // 알림 엔티티 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.COLLECTIONS)
                .content(message)
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
    public void handleProgramDeadlineEvent(DeadlineEvent deadlineEvent) {

        Program program = deadlineEvent.getProgram();

        // 전체 멤버 리스트 가져오기
        List<Member> members = memberRepository.findByStatus(MemberStatus.ACTIVE);

        // 알림 엔티티에 메세지 insert
        Notification notification = Notification.builder()
                .notificationType(NotificationType.SOON_CLOSE)
                .content(program.getTitle() + " 마감이 임박했어요")
                .resourceIdx(program.getIdx())
                .resourceType(program.getProgramType())
                .build();

        // 알림 DB에 저장
        notificationService.addNotification(notification, members);

        // 알림 전송
        notificationSender.sendNotification(notification, members);

    }
}
