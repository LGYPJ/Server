package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.program.service.NetworkingService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/networkings")
public class NetworkingController {

    private final NetworkingService networkingService;

    /*
    // 보류
    // 네트워킹 모아보기
    @GetMapping("/collection")
    public GetProgramListRes getSeminarCollectionList() {
        // validation 처리

        GetProgramListRes getProgramListRes = networkingService.findNetworkingCollectionList();

        return getProgramListRes;
    }

     */

    // 이번 달 네트워킹
    @GetMapping("/this-month")
    public ProgramDto getThisMonthNetworking() {
        return networkingService.findThisMonthNetworking();
    }

    // 예정된 네트워킹
    @GetMapping("/next-month")
    public ProgramDto getNextNetworking() {
        return networkingService.findReadyNetworking();
    }

    // 마감된 네트워킹
    @GetMapping("/closed")
    public List<ProgramDto> getClosedNetworkingList() {
        return networkingService.findClosedNetworkingList();
    }

    //홈 화면 네트워킹 리스트
    @GetMapping("/main")
    public List<ProgramDto> getMainNetworkingList() {
        return networkingService.findMainNetworkingList();
    }

    // 네트워킹 상세 정보
    @GetMapping("/info")
    public ProgramInfoDto getNetworkingDetailInfo(@RequestBody ProgramDetailReq programDetailReq) {
        // validation

        return networkingService.findNetworkingDetails(programDetailReq);
    }


    // 네트워킹 신청자 리스트
    @GetMapping("/{networking-idx}/participants")
    public List<ParticipantDto> getNetworkingParticipantList(@PathVariable(name = "networking-idx") Long networkingIdx) {
        // validation 처리
        if(networkingIdx == null)
            throw new RestApiException(ErrorCode.EMPTY_PARAMETER);

        return networkingService.findNetworkingParticipantsList(networkingIdx);
    }

}