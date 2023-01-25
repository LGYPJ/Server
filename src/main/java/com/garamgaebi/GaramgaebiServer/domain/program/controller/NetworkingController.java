package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.program.service.NetworkingService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "NetworkingController", description = "네트워킹 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/networkings")
public class NetworkingController {

    private final NetworkingService networkingService;

    // 이번 달 네트워킹
    @Operation(summary = "이번 달 네트워킹 조회", description = "이번 달에 있는 네트워킹을 한 건을 조회합니다.")
    @GetMapping("/this-month")
    public BaseResponse<ProgramDto> getThisMonthNetworking() { return new BaseResponse<>(networkingService.findThisMonthNetworking()); }

    // 예정된 네트워킹
    @GetMapping("/next-month")
    public BaseResponse<ProgramDto> getNextNetworking() {
        return new BaseResponse<>(networkingService.findReadyNetworking());
    }

    // 마감된 네트워킹
    @GetMapping("/closed")
    public BaseResponse<List<ProgramDto>> getClosedNetworkingList() {
        return new BaseResponse<>(networkingService.findClosedNetworkingList());
    }

    //홈 화면 네트워킹 리스트
    @GetMapping("/main")
    public BaseResponse<List<ProgramDto>> getMainNetworkingList() {
        return new BaseResponse<>(networkingService.findMainNetworkingList());
    }

    // 네트워킹 상세 정보
    @GetMapping("/info")
    public BaseResponse<ProgramInfoDto> getNetworkingDetailInfo(@RequestBody @Valid ProgramDetailReq programDetailReq) {

        return new BaseResponse<>(networkingService.findNetworkingDetails(programDetailReq));
    }

    // 네트워킹 신청자 리스트
    @GetMapping("/{networking-idx}/participants")
    public BaseResponse<List<ParticipantDto>> getNetworkingParticipantList(@PathVariable(name = "networking-idx") Long networkingIdx) {

        return new BaseResponse<>(networkingService.findNetworkingParticipantsList(networkingIdx));
    }

}