package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.program.service.NetworkingService;
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

    //홈 화면 네트워킹 리스트
    @GetMapping("/main")
    public List<ProgramDto> getMainSeminarList() {
        // validation 처리

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

        return networkingService.findNetworkingParticipantsList(networkingIdx);
    }

}