package com.garamgaebi.GaramgaebiServer.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {
    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("DEBUG, message: " + message);
        System.out.println("DEBUG, accessor: " + accessor);
        String sessionId = accessor.getSessionId();
        String userIdx = accessor.getFirstNativeHeader("userIdx");
        log.info("DEBUG: userIdx = " + userIdx);

        RequestContextHolder.getRequestAttributes();

        switch (accessor.getCommand()) {
            case CONNECT:
                System.out.println("DEBUG, connected sessionId: " + sessionId + "userIdx: " + userIdx);
                break;
            case DISCONNECT:
                System.out.println("DEBUG, disconnected sessionId: " + sessionId + "userIdx: " + userIdx);
                break;
            case SUBSCRIBE:
                System.out.println("DEBUG, subscribe sessionId: " + sessionId + "userIdx: " + userIdx);
                break;
            default:
                break;
        }
    }
}
