package com.garamgaebi.GaramgaebiServer.global.util;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompHandler implements ChannelInterceptor {
    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("DEBUG, message: " + message);
        System.out.println("DEBUG, accessor: " + accessor);
        String sessionId = accessor.getSessionId();
        switch (accessor.getCommand()) {
            case CONNECT:
                System.out.println("DEBUG, connected: " + sessionId);
                break;
            case DISCONNECT:
                System.out.println("DEBUG, disconnected: " + sessionId);
                break;
            case SUBSCRIBE:
                System.out.println("DEBUG, subscribe: " + sessionId);
                break;
            default:
                break;
        }
    }
}
