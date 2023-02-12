package com.garamgaebi.GaramgaebiServer.domain.ice_breaking;

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
        System.out.println("message: " + message);
        System.out.println("accessor: " + accessor);
        String sessionId = accessor.getSessionId();
        switch (accessor.getCommand()) {
            case CONNECT:
                System.out.println("connected: " + sessionId);
                break;
            case DISCONNECT:
                System.out.println("disconnected: " + sessionId);
                break;
            case SUBSCRIBE:
                System.out.println("subscribe: " + sessionId);
                break;
            default:
                break;
        }
    }

    // roomId까지는 가져올 수 있는데 memberIdx가 아닌 sessionId로 와서,, 못 쓸 듯 이건
}
