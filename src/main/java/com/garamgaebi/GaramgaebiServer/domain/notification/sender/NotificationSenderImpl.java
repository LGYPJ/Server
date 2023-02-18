package com.garamgaebi.GaramgaebiServer.domain.notification.sender;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberFcm;
import com.garamgaebi.GaramgaebiServer.domain.entity.Notification;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import jakarta.annotation.PostConstruct;
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
public class NotificationSenderImpl implements NotificationSender {

    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

    // 메시징만 권한 설정
    @Value("${fcm.key.scope}")
    private String fireBaseScope;

    // fcm 기본 설정 진행 -> PostConstruct로 스프링 설정이 끝난 뒤에 init(firebase에 앱 등록) 되도록
    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                                    .createScoped(List.of(fireBaseScope)))
                    .build();
            FirebaseApp.initializeApp(options);

        } catch(IOException e) {
            // spring 올릴 때 알림서버 미작동 -> 바로 어플리케이션 죽임
            log.error("FIREBASE CONNECTION ERROR");
            throw new RuntimeException();
        }
    }

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
        if(fcmTokenList.size() != 0) {
            sendByTokenList(fcmTokenList, notificationDto);
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

        // 알림 발송
        if(fcmTokenList.size() != 0) {
            sendByTokenList(fcmTokenList, notificationDto);
        }
    }

    // 알림 보내기
    private void sendByTokenList(List<String> tokenList, NotificationDto notificationDto) {

        List<Message> messages = tokenList.stream().map(token -> Message.builder()
                .putData("notificationType", notificationDto.getNotificationType().toString())
                .putData("content", notificationDto.getContent())
                .putData("resourceIdx", notificationDto.getResourceIdx().toString())
                .putData("resourceType", notificationDto.getResourceType().toString())
                .setToken(token)
                .build()).collect(Collectors.toList());

        BatchResponse response;
        try {
            // firebase 서버로 알림 발송
            response = FirebaseMessaging.getInstance().sendAll(messages);

            // 요청에 대한 응답 처리
            if(response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();

                for(int i = 0; i < responses.size(); i++) {
                    if(!responses.get(i).isSuccessful()) {
                        failedTokens.add(tokenList.get(i));
                    }
                }
                if(!failedTokens.isEmpty()) {
                    log.error("FAIL SEND NOTIFICATION ERROR : TOKEN = {}", failedTokens);
                }
            }

        } catch (FirebaseMessagingException e) {
            log.error("FAIL SEND ALL NOTIFICATION ERROR : NOTIFICATION = {}", notificationDto);
        }
    }

}
