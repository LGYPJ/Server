package com.garamgaebi.GaramgaebiServer.global.util.firebase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.NotificationDto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.google.gson.JsonParseException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class FcmService {
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
            // spring 올릴 때 알림서버 미작동 -> 에러 로그
            log.error("FIREBASE CONNECTION ERROR");
        }
    }

    public void sendMessageTo(List<String> targetTokenList, NotificationDto notificationDto) throws FirebaseMessagingException {
        MulticastMessage message = MulticastMessage.builder()
                .putData("notificationType", notificationDto.getNotificationType().toString())
                .putData("content", notificationDto.getContent())
                .putData("programIdx", notificationDto.getResourceIdx().toString())
                .putData("programType", notificationDto.getResourceType().toString())
                .addAllTokens(targetTokenList)
                .build();

        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);

        if (response.getFailureCount() > 0) {
            List<SendResponse> responses = response.getResponses();
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    log.error("firebase messaging fail response : {} {}", responses.get(i).getException(), targetTokenList.get(i));
                }
            }

        }
    }

}
