package com.garamgaebi.GaramgaebiServer.admin.program.controller;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.*;
import com.garamgaebi.GaramgaebiServer.admin.program.service.AdminProgramService;
import com.garamgaebi.GaramgaebiServer.admin.program.service.AdminProgramServiceImpl;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AdminProgramController", description = "관리자 페이지 프로그램 컨트롤(담당자:줄리아)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminProgramController {

    private final AdminProgramService adminProgramService;

    // 세미나 등록
    @PostMapping("/seminar")
    @ResponseBody
    public BaseResponse<ProgramRes> writeSeminar(@RequestBody @Valid SeminarDto seminarDto) {

        return new BaseResponse<>(adminProgramService.addSeminar(seminarDto));
    }

    // 네트워킹 등록
    @PostMapping("/networking")
    @ResponseBody
    public BaseResponse<ProgramRes> writeNetworking(@RequestBody @Valid NetworkingDto networkingDto) {

        return new BaseResponse<>(adminProgramService.addNetworking(networkingDto));
    }

    // 세미나 수정
    @PatchMapping("/seminar")
    @ResponseBody
    public BaseResponse<ProgramRes> modifySeminar(@RequestBody @Valid PatchSeminarDto patchSeminarDto) {

        return new BaseResponse<>(adminProgramService.modifySeminar(patchSeminarDto));
    }

    // 네트워킹 수정
    @PatchMapping("/networking")
    @ResponseBody
    public BaseResponse<ProgramRes> modifyNetworking(@RequestBody @Valid PatchNetworkingDto patchNetworkingDto) {

        return new BaseResponse<>(adminProgramService.modifyNetworking(patchNetworkingDto));
    }

    // 글 삭제
    @DeleteMapping("/program/{program-idx}")
    @ResponseBody
    public void deleteProgram(@PathVariable(name = "program-idx") Long programIdx) {

        adminProgramService.deleteProgram(programIdx);
    }
    //deleteProgram BaseResponse 적용을 어케...


    // 발표자료 추가
    @PostMapping("/seminar/{seminar-idx}/presentation")
    @ResponseBody
    public BaseResponse<PresentationRes> writePresentation(@PathVariable(name = "seminar-idx") Long seminarIdx,
                                                           @RequestBody @Valid PostPresentationDto postPresentationDto) {

        return new BaseResponse<>(adminProgramService.addPresentation(seminarIdx, postPresentationDto));
    }

    // 발표자료 수정
    @PatchMapping("/seminar/presentation")
    @ResponseBody
    public BaseResponse<PresentationRes> modifyPresentation(@RequestBody @Valid PostPresentationDto postPresentationDto) {

        return new BaseResponse<>(adminProgramService.modifyPresentation(postPresentationDto));
    }

    // 발표자료 삭제
    @DeleteMapping("/seminar/presentation/{presentation-idx}")
    @ResponseBody
    public void deletePresentation(@PathVariable(name = "presentation-idx") Long presentationIdx) {

        adminProgramService.deletePresentation(presentationIdx);
    }
    //BaseResponse 적용 어떻게..?

    // 프로그램 오픈
    @PatchMapping("/program/{program-idx}/open")
    @ResponseBody
    public BaseResponse<ProgramRes> openProgram(@PathVariable(name = "program-idx") Long programIdx) {
        // validation..?

        return new BaseResponse<>(adminProgramService.openProgram(programIdx));
    }

}