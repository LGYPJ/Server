package com.garamgaebi.GaramgaebiServer.global.util;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MemberRoomDeleteReq;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service.GameService;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final SimpMessageSendingOperations sendingOperations;
    private final GameService gameService;

    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        String memberIdx = accessor.getFirstNativeHeader("memberIdx");
        String roomId = accessor.getFirstNativeHeader("roomId");
        String nextMemberIdx = accessor.getFirstNativeHeader("nextMemberIdx");

        log.info("DEBUG: memberIdx = {}", memberIdx);
        log.info("DEBUG: roomId = {}", roomId);
        log.info("DEBUG: nextMemberIdx = {}", nextMemberIdx);

        RequestContextHolder.getRequestAttributes();

        switch (accessor.getCommand()) {
            case CONNECT:
                break;
            case DISCONNECT:
                sendingOperations.convertAndSend("/topic/game/room/" + roomId, "DISCONNECT");
                gameService.deleteMemberFromGameRoom(new MemberRoomDeleteReq(roomId, Long.parseLong(nextMemberIdx)), Long.parseLong(memberIdx));
                break;
            case SUBSCRIBE:
                break;
            default:
                break;
        }
    }
}
