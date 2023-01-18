package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.GetProgramListRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.service.SeminarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seminars")
public class SeminarController {

    private final SeminarService seminarService;

    // 세미나 모아보기
    @GetMapping("")
    public GetProgramListRes getSeminarList() {
        // validation 처리

        return new GetProgramListRes(
                seminarService.findThisMonthSeminar(),
                seminarService.findReadySeminar(),
                seminarService.findClosedSeminarList()
        );
    }

    // 이번달 세미나
    @GetMapping("/this-month")
    public ProgramDto getThisMonthSeminar() {
        // validation 처리

        ProgramDto thisMonthSeminar = seminarService.findThisMonthSeminar();
        if(thisMonthSeminar == null) {
            // 예외처리
        }

        return thisMonthSeminar;
    }
}
