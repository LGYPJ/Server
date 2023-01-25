package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
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

    // 이번 달 세미나
    @GetMapping("/this-month")
    public ProgramDto getThisMonthSeminar() {
        return seminarService.findThisMonthSeminar();
    }

    // 예정된 세미나
    @GetMapping("/next-month")
    public ProgramDto getNextSeminar() {
        return seminarService.findReadySeminar();
    }

    // 마감된 세미나
    @GetMapping("/closed")
    public List<ProgramDto> getClosedSeminarList() {
        return seminarService.findClosedSeminarsList();
    }

    //홈 화면 세미나 리스트
    @GetMapping("/main")
    public List<ProgramDto> getMainSeminarList() {
        // validation 처리

        return seminarService.findMainSeminarList();
    }

    // 세미나 상세정보
    @GetMapping("/info")
    public ProgramInfoDto getSeminarDetailInfo(@RequestBody ProgramDetailReq programDetailReq) {
        // validation 처리
        if(programDetailReq.getProgramIdx() == null || programDetailReq.getMemberIdx() == null) {
            // 예외 처리
        }

        return seminarService.findSeminarDetails(programDetailReq);
    }

    // 세미나 신청자 리스트
    @GetMapping("/{seminar-idx}/participants")
    public List<ParticipantDto> getSemianrParticipantList(@PathVariable(name = "seminar-idx") Long seminarIdx) {

        // validation 처리

        return seminarService.findSeminarParticipantsList(seminarIdx);
    }

    // 세미나 발표 리스트
    @GetMapping("/{seminar-idx}/presentations")
    public List<PresentationDto> getSeminarPresentationList(@PathVariable(name = "seminar-idx") Long seminarIdx) {
        // validation 처리

        if(seminarIdx == null) {
            // 예외처리
        }

        return seminarService.findSeminarPresentationList(seminarIdx);
    }

}
