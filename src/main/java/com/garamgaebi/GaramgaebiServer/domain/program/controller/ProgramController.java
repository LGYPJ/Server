package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.service.ProgramService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;

    // 예정된 내 모임
    @GetMapping("/{member-idx}/ready")
    public BaseResponse<List<ProgramDto>> getMemberReadyProgramList(@PathVariable(name = "member-idx") Long memberIdx) {

        return new BaseResponse<>(programService.findMemberReadyProgramList(memberIdx));
    }

    // 지난 내 모임
    @GetMapping("/{member-idx}/close")
    public BaseResponse<List<ProgramDto>> getMemberClsoedProgramList(@PathVariable(name = "member-idx") Long memberIdx) {

        return new BaseResponse<>(programService.findMemberClosedProgramList(memberIdx));
    }
}
