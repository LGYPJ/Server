package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.service.ProgramService;
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

    private ProgramService programService;

    @GetMapping("/{member-idx}/reday")
    public List<ProgramDto> getMemberReadyProgramList(@PathVariable(name = "member-idx") Long memberIdx) {
        // validation 처리

        return programService.findMemberReadyProgramList(memberIdx);
    }

    @GetMapping("/{member-idx}/close")
    public List<ProgramDto> getMemberClsoedProgramList(@PathVariable(name = "member-idx") Long memberIdx) {
        // validation 처리

        return programService.findMemberClosedProgramList(memberIdx);
    }
}
