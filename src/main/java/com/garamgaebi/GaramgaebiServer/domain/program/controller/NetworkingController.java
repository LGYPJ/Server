package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.GetProgramListRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.service.NetworkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/networkings")
public class NetworkingController {

    private final NetworkingService networkingService;

    // 네트워킹 모아보기
    @GetMapping("")
    public GetProgramListRes getNetworkingList() {
        // validation 처리

        return new GetProgramListRes(
                networkingService.findThisMonthNetworking(),
                networkingService.findReadyNetworking(),
                networkingService.findClosedNetworking()
        );
    }

    // 이번달 세미나
    @GetMapping("/this-month")
    public ProgramDto getThisMonthNetworking() {
        // validation 처리

        ProgramDto thisMonthNetworking = networkingService.findThisMonthNetworking();
        if(thisMonthNetworking == null) {
            // 예외처리
        }

        return thisMonthNetworking;
    }
}