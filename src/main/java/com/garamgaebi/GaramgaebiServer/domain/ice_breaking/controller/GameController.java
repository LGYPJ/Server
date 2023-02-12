package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.controller;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramGameroom;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MembersGetReq;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MembersGetRes;
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
    public BaseResponse<List<ProgramGameroom>> getRooms(@PathVariable Long programIdx) {
        return new BaseResponse<>(gameService.getRoomsByProgram(programIdx));
    }

    // gameRoomIdx로 members 조회
    @GetMapping("/members")
    @ResponseBody
    public BaseResponse<List<MembersGetRes>> getMembers(@RequestBody MembersGetReq membersGetReq) {
        return new BaseResponse<>(gameService.getMembersByGameRoomIdx(membersGetReq.getRoomId()));
    }
}
