package com.garamgaebi.GaramgaebiServer.global.util.firebase;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.MemberFcm;
import com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.Notification;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationSenderImpl implements NotificationSender {

    private final FcmService fcmService;


    // 여러 멤버 알림 발송
    @Override
    public void sendNotification(Notification notification, List<Member> members) {
        // 알림 메세지 DTO 빌드
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
        try {
            fcmService.sendMessageTo(fcmTokenList, notificationDto);
        } catch(Exception e) {
            log.error("FIREBASE MESSAGING FAIL : {}", e.getStackTrace());
        }

    }

    // 한 멤버 알림 발송
    @Override
    public void sendNotification(Notification notification, Member member) {
        // 알림 메세지 DTO 빌드
        NotificationDto notificationDto = new NotificationDto(
                notification.getNotificationType(),
                notification.getContent(),
                notification.getResourceIdx(),
                notification.getResourceType()
        );

        // 해당 유저의 fcm Token list로 알림 전달
        List<String> fcmTokenList = new ArrayList<>();

        // fcm 토큰 리스트 추가
        for (MemberFcm memberFcm : member.getMemberFcms()) {
            fcmTokenList.add(memberFcm.getFcmToken());
        }

        try {
            fcmService.sendMessageTo(fcmTokenList, notificationDto);
        } catch(Exception e) {
            log.error("FIREBASE MESSAGING FAIL : {}", e.getStackTrace());
        }

    }
}
