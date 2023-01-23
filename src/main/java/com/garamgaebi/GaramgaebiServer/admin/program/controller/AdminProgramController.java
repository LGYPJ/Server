package com.garamgaebi.GaramgaebiServer.admin.program.controller;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.*;
import com.garamgaebi.GaramgaebiServer.admin.program.repository.AdminProgramRepository;
import com.garamgaebi.GaramgaebiServer.admin.program.service.AdminProgramService;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminProgramController {

    private final AdminProgramService adminProgramService;
    private final AdminProgramRepository adminProgramRepository;
    private final ProgramRepository programRepository;

    // 세미나 등록
    @PostMapping("/seminar")
    @ResponseBody
    public Long writeSeminar(@RequestBody SeminarDto seminarDto) {
        // validation

        return adminProgramService.addSeminar(seminarDto);
    }

    // 네트워킹 등록
    @PostMapping("/networking")
    @ResponseBody
    public Long writeNetworking(@RequestBody NetworkingDto networkingDto) {
        // validation

        return adminProgramService.addNetworking(networkingDto);
    }

    // 세미나 수정
    @PatchMapping("/seminar")
    @ResponseBody
    public Long modifySeminar(@RequestBody PatchSeminarDto patchSeminarDto) {
        // validation

        return adminProgramService.modifySeminar(patchSeminarDto);
    }

    // 네트워킹 수정
    @PatchMapping("/networking")
    @ResponseBody
    public Long modifyNetworking(@RequestBody PatchNetworkingDto patchNetworkingDto) {
        // validation

        return adminProgramService.modifyNetworking(patchNetworkingDto);
    }

    // 글 삭제
    @DeleteMapping("/program/{program-idx}")
    @ResponseBody
    public void deleteProgram(@PathVariable(name = "program-idx") Long programIdx) {
        // validation

        adminProgramService.deleteProgram(programIdx);
    }

    // 발표자료 추가
    @PostMapping("/seminar/{seminar-idx}/presentation")
    @ResponseBody
    public Long writePresentation(@PathVariable(name = "seminar-idx") Long seminarIdx,
                                  @RequestBody PresentationDto presentationDto) {
        // validation

        return adminProgramService.addPresentation(seminarIdx, presentationDto);
    }

    // 발표자료 수정
    @PatchMapping("/seminar/presentation")
    @ResponseBody
    public Long modifyPresentation(@RequestBody PresentationDto presentationDto) {
        // validation

        return adminProgramService.modifyPresentation(presentationDto);
    }

    // 발표자료 삭제
    @DeleteMapping("/seminar/presentation/{presentation-idx}")
    @ResponseBody
    public void deletePresenatation(@PathVariable Long presentationIdx) {
        // validation

        adminProgramService.deletePresentation(presentationIdx);
    }

    // 프로그램 오픈
    @PatchMapping("/program/{program-idx}/open")
    @ResponseBody
    public Long openProgram(@PathVariable(name = "program-idx") Long programIdx) {
        // validation

        return adminProgramService.openProgram(programIdx);
    }

}
