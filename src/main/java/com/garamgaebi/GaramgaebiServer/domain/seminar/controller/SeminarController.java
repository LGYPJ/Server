package com.garamgaebi.GaramgaebiServer.domain.seminar.controller;

import com.garamgaebi.GaramgaebiServer.domain.seminar.dto.SeminarApplyReq;
import com.garamgaebi.GaramgaebiServer.domain.seminar.dto.SeminarCancelReq;
import com.garamgaebi.GaramgaebiServer.domain.seminar.service.SeminarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seminars")
@RequiredArgsConstructor
public class SeminarController {

    private final SeminarService seminarService;

    @PostMapping("/application/{user-idx}}")
    public void applySeminar(@RequestBody SeminarApplyReq seminarApplyReq, @RequestParam Long userIdx) {
        // 형식적 validation

        seminarService.apply(seminarApplyReq);

        // 반환값 협의
    }

    @PatchMapping("/application/{user-idx}")
    public void cancelApplySeminar(@RequestBody SeminarCancelReq seminarCancelReq, @RequestParam Long userIdx) {
        // 형식적 validation

        seminarService.cancel(seminarCancelReq);

        // 반환값 협의
    }
}
