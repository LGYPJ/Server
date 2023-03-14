package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.controller;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Websocket Controller", description = "웹소켓 컨트롤러(담당자: 애플)")
@RestController
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/game/message")
    @Operation(summary = "roomId로 message 전송", description = "roomId를 구독하고 있는 사용자에게 메세지를 전달", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    public void enter(MessageDto message) {
        sendingOperations.convertAndSend("/topic/game/room/" + message.getRoomId(), message);
    }
}
