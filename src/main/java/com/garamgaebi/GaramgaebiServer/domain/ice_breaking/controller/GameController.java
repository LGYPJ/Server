package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.controller;

import com.garamgaebi.GaramgaebiServer.domain.entity.GameRoom;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service.GameService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    // programIdx로 게임방 조회
    @GetMapping("/{programIdx}/rooms")
    @ResponseBody
    public BaseResponse<List<GameRoom>> getRooms(@PathVariable Long programIdx) {
        return new BaseResponse<>(gameService.getRoomsByProgram(programIdx));
    }
}
