package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.controller;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/game/message")
    public void enter(MessageDto message) {
        if (MessageDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하였습니다.");
        }
        sendingOperations.convertAndSend("/topic/game/room" + message.getRoomId(), message);
    }
}
