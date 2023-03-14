package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.controller;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity.ProgramGameroom;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service.GameServiceImpl;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import com.garamgaebi.GaramgaebiServer.global.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ice_breaking Controller", description = "아이스 브레이킹 컨트롤러(담당자: 애플)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameServiceImpl gameService;
    private final JwtTokenProvider jwtTokenProvider;

    // programIdx로 게임방 조회
    @Operation(summary = "게임방 리스트 조회", description = "programIdx로 해당하는 네트워킹의 게임방을 조회", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/{programIdx}/rooms")
    @ResponseBody
    public BaseResponse<List<ProgramGameroom>> getRooms(@PathVariable Long programIdx) {
        return new BaseResponse<>(gameService.getRoomsByProgram(programIdx));
    }

    // gameRoomIdx로 members 조회
    @Operation(summary = "멤버 리스트 조회", description = "gameRoomIdx로 해당하는 게임방의 멤버 리스트를 조회", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/members")
    @ResponseBody
    public BaseResponse<List<MembersGetRes>> getMembers(@RequestBody MembersGetReq membersGetReq) {
        return new BaseResponse<>(gameService.getMembersByGameRoomIdx(membersGetReq.getRoomId()));
    }

    // gameRoom에 member 등록
    @Operation(summary = "멤버 등록", description = "roomId로 멤버를 게임방에 등록", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/member")
    @ResponseBody
    public BaseResponse<MemberRoomRes> postMemberToGameRoom(@RequestBody MemberRoomPostReq memberRoomReq) {
        return new BaseResponse<>(gameService.registerMemberToGameRoom(memberRoomReq,
                jwtTokenProvider.getMemberIdx()
        ));
    }

    // gameRoom에 member 삭제
    @Operation(summary = "멤버 삭제", description = "roomId로 멤버를 게임방에서 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @DeleteMapping("/member")
    @ResponseBody
    public BaseResponse<String> deleteMemberFromGameRoom(@RequestBody MemberRoomDeleteReq memberRoomDeleteReq) {
        return new BaseResponse<>(gameService.deleteMemberFromGameRoom(
                memberRoomDeleteReq,
                jwtTokenProvider.getMemberIdx()
        ));
    }

    // 랜덤 이미지 조회
    @Operation(summary = "랜덤 이미지 30장 조회", description = "programIdx를 seed로 하여 랜덤 이미지 30장 조회", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/{programIdx}/images")
    @ResponseBody
    public BaseResponse<List<String>> getGameImages(@PathVariable Long programIdx) {
        return new BaseResponse<>(gameService.getImageUrls(programIdx));
    }

    // 게임방 현재 이미지 인덱스 증가
    @Operation(summary = "게임방 이미지 인덱스 증가", description = "현재 게임방의 current image index 증가", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PatchMapping("/current-idx")
    @ResponseBody
    public BaseResponse<String> patchCurrentImgIdx(@RequestBody CurrentImgIdxReq currentImgIdxReq) {
        return new BaseResponse<>(gameService.patchCurrentImgIdx(
                currentImgIdxReq,
                jwtTokenProvider.getMemberIdx()
        ));
    }

    // 게임방 진행중 유무 조회
    @Operation(summary = "게임방 진행중 유무 조회", description = "전달한 roomId 게임방의 진행중 유무를 boolean으로 조회", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/isStarted")
    @ResponseBody
    public BaseResponse<Boolean> getIsStarted(@RequestBody IsStartedReq isStartedReq) {
        return new BaseResponse<>(gameService.getIsStarted(isStartedReq.getRoomId()));
    }

    // 게임방 상태를 진행중으로 변경
    @Operation(summary = "게임방 상태 진행중으로 변경", description = "전달한 roomId 게임방의 상태를 진행중으로 변경", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PatchMapping("/startGame")
    @ResponseBody
    public BaseResponse<String> startGame(@RequestBody IsStartedReq isStartedReq) {
        return new BaseResponse<>(gameService.patchIsStarted(isStartedReq.getRoomId()));
    }
}
