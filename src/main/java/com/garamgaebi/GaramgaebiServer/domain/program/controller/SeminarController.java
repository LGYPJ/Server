package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.GetProgramListRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDetailReq;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramInfoDto;
import com.garamgaebi.GaramgaebiServer.domain.program.service.SeminarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seminars")
public class SeminarController {

    private final SeminarService seminarService;

    /*
    // 보류
    // 세미나 모아보기

    @GetMapping("/collection")
    public GetProgramListRes getSeminarCollectionList() {
        // validation 처리

        GetProgramListRes getProgramListRes = seminarService.findSeminarCollectionList();

        return getProgramListRes;
    }

     */

    //홈 화면 세미나 리스트
    @GetMapping("/main")
    public List<ProgramDto> getMainSeminarList() {
        // validation 처리

        return seminarService.findMainSeminarList();
    }

    @GetMapping("/info")
    public ProgramInfoDto getSeminarDetailInfo(@RequestBody ProgramDetailReq programDetailReq) {
        // validation 처리
        if(programDetailReq.getProgramIdx() == null || programDetailReq.getMemberIdx() == null) {
            // 예외 처리
        }

        return seminarService.findSeminarDetails(programDetailReq.getMemberIdx(), programDetailReq.getProgramIdx());

    }

}
