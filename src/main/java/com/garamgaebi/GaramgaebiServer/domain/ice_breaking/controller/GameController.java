package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.controller;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramGameroom;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.*;
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
    @PostMapping("/members")
    @ResponseBody
    public BaseResponse<List<MembersGetRes>> getMembers(@RequestBody MembersGetReq membersGetReq) {
        return new BaseResponse<>(gameService.getMembersByGameRoomIdx(membersGetReq.getRoomId()));
    }

    // gameRoom에 member 등록
    @PostMapping("/member")
    @ResponseBody
    public BaseResponse<MemberRoomRes> postMemberToGameRoom(@RequestBody MemberRoomReq memberRoomReq) {
        return new BaseResponse<>(gameService.registerMemberToGameRoom(memberRoomReq));
    }

    // gameRoom에 member 삭제
    @DeleteMapping("/member")
    @ResponseBody
    public BaseResponse<MemberRoomRes> deleteMemberFromGameRoom(@RequestBody MemberRoomReq memberRoomReq) {
        return new BaseResponse<>(gameService.deleteMemberFromGameRoom(memberRoomReq));
    }

    // 랜덤 이미지 조회
    @GetMapping("/{programIdx}/images")
    @ResponseBody
    public BaseResponse<List<String>> getGameImages(@PathVariable Long programIdx) {
        return new BaseResponse<>(gameService.getImageUrls(programIdx));
    }

    // 현재 진행중인 게임 인덱스 조회
    @PostMapping("/current-idx")
    @ResponseBody
    public BaseResponse<Integer> getCurrentImgIdx(@RequestBody CurrentImgIdxReq currentImgIdxReq) {
        return new BaseResponse<>(gameService.getCurrentImgIdx(currentImgIdxReq.getRoomId()));
    }

    @PatchMapping("/current-idx")
    @ResponseBody
    public BaseResponse<String> patchCurrentImgIdx(@RequestBody CurrentImgIdxReq currentImgIdxReq) {
        return new BaseResponse<>(gameService.patchCurrentImgIdx(currentImgIdxReq.getRoomId()));
    }
}
