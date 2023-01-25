package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.program.service.SeminarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seminars")
public class SeminarController {

    private final SeminarService seminarService;

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
    public List<ProgramDto> getMainSeminarList() { return seminarService.findMainSeminarList(); }

    // 세미나 상세정보
    @GetMapping("/info")
    public ProgramInfoDto getSeminarDetailInfo(@RequestBody @Valid ProgramDetailReq programDetailReq) {

        return seminarService.findSeminarDetails(programDetailReq);
    }

    // 세미나 신청자 리스트
    @GetMapping("/{seminar-idx}/participants")
    public List<ParticipantDto> getSeminarParticipantList(@PathVariable(name = "seminar-idx") Long seminarIdx) {

        return seminarService.findSeminarParticipantsList(seminarIdx);
    }

    // 세미나 발표 리스트
    @GetMapping("/{seminar-idx}/presentations")
    public List<PresentationDto> getSeminarPresentationList(@PathVariable(name = "seminar-idx") Long seminarIdx) {

        return seminarService.findSeminarPresentationList(seminarIdx);
    }

}
