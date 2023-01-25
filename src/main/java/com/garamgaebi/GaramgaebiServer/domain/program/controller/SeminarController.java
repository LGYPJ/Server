package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.program.service.SeminarService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
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
    public BaseResponse<ProgramDto> getThisMonthSeminar() {
        return new BaseResponse<>(seminarService.findThisMonthSeminar());
    }

    // 예정된 세미나
    @GetMapping("/next-month")
    public BaseResponse<ProgramDto> getNextSeminar() {
        return new BaseResponse<>(seminarService.findReadySeminar());
    }

    // 마감된 세미나
    @GetMapping("/closed")
    public BaseResponse<List<ProgramDto>> getClosedSeminarList() {
        return new BaseResponse<>(seminarService.findClosedSeminarsList());
    }

    //홈 화면 세미나 리스트
    @GetMapping("/main")
    public BaseResponse<List<ProgramDto>> getMainSeminarList() { return new BaseResponse<>(seminarService.findMainSeminarList()); }

    // 세미나 상세정보
    @GetMapping("/info")
    public BaseResponse<ProgramInfoDto> getSeminarDetailInfo(@RequestBody @Valid ProgramDetailReq programDetailReq) {

        return new BaseResponse<>(seminarService.findSeminarDetails(programDetailReq));
    }

    // 세미나 신청자 리스트
    @GetMapping("/{seminar-idx}/participants")
    public BaseResponse<List<ParticipantDto>> getSeminarParticipantList(@PathVariable(name = "seminar-idx") Long seminarIdx) {

        return new BaseResponse<>(seminarService.findSeminarParticipantsList(seminarIdx));
    }

    // 세미나 발표 리스트
    @GetMapping("/{seminar-idx}/presentations")
    public BaseResponse<List<PresentationDto>> getSeminarPresentationList(@PathVariable(name = "seminar-idx") Long seminarIdx) {

        return new BaseResponse<>(seminarService.findSeminarPresentationList(seminarIdx));
    }

}
