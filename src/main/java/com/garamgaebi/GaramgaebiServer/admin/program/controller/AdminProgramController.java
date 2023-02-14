package com.garamgaebi.GaramgaebiServer.admin.program.controller;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.*;
import com.garamgaebi.GaramgaebiServer.admin.program.service.AdminProgramService;
import com.garamgaebi.GaramgaebiServer.admin.program.service.AdminProgramServiceImpl;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramPayStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "AdminProgramController", description = "관리자 페이지 프로그램 컨트롤(담당자:줄리아)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminProgramController {

    private final AdminProgramService adminProgramService;

    // 세미나 글 목록 조회
    @GetMapping("/seminar")
    @ResponseBody
    public BaseResponse<List<GetProgramRes>> findSeminarList(@RequestParam(name = "payment") ProgramPayStatus payment,
                                                             @RequestParam(name = "status")ProgramStatus status,
                                                             @RequestParam(name = "period-start", required = false)LocalDateTime start,
                                                             @RequestParam(name = "period-end", required = false) LocalDateTime end)
    {
        return new BaseResponse<>(adminProgramService.findSeminarList(payment, status, start, end));
    }

    // 세미나 글 조회
    @GetMapping("/seminar/{seminar-idx}")
    @ResponseBody
    public BaseResponse<GetProgramDto> findSeminar(@PathVariable("seminar-idx") Long seminarIdx) {

        return new BaseResponse<>(adminProgramService.findSeminar(seminarIdx));
    }

    // 세미나 등록
    @PostMapping("/seminar")
    @ResponseBody
    public BaseResponse<ProgramRes> writeSeminar(@RequestBody @Valid SeminarDto seminarDto) {

        return new BaseResponse<>(adminProgramService.addSeminar(seminarDto));
    }

    // 네트워킹 글 목록 조회
    @GetMapping("/networking")
    @ResponseBody
    public BaseResponse<List<GetProgramRes>> findNetworkingList(@RequestParam(name = "payment") ProgramPayStatus payment,
                                                             @RequestParam(name = "status")ProgramStatus status,
                                                             @RequestParam(name = "period-start", required = false)LocalDateTime start,
                                                             @RequestParam(name = "period-end", required = false) LocalDateTime end)
    {
        return new BaseResponse<>(adminProgramService.findNetworkingList(payment, status, start, end));
    }

    // 네트워킹 글 조회
    @GetMapping("/networking/{networking-idx}")
    @ResponseBody
    public BaseResponse<GetProgramDto> findNetworking(@PathVariable("seminar-idx") Long networkingIdx) {

        return new BaseResponse<>(adminProgramService.findNetworking(networkingIdx));
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

    // 프로그램 삭제
    @DeleteMapping("/program/{program-idx}")
    @ResponseBody
    public BaseResponse<ProgramRes> deleteProgram(@PathVariable(name = "program-idx") Long programIdx,
                                        @RequestBody DeleteDto deleteDto) {

        return new BaseResponse<>(adminProgramService.deleteProgram(programIdx, deleteDto));
    }

    // 발표자료 리스트 조회
    @GetMapping("/seminar/{seminar-idx}/presentation")
    @ResponseBody
    public BaseResponse<List<GetPresentationDto>> findPresentationList(@PathVariable("seminar-idx") Long seminarIdx) {
        return new BaseResponse<>(adminProgramService.findPresentationList(seminarIdx));
    }

    // 발표자료 상세 조회
    @GetMapping("/presentation/{presentation-idx}")
    @ResponseBody
    public BaseResponse<GetPresentationDto> findPresentation(@PathVariable("presentation-idx") Long presentationIdx) {
        return new BaseResponse<>(adminProgramService.findPresentation(presentationIdx));
    }

    // 발표자료 추가
    @PostMapping("/seminar/{seminar-idx}/presentation")
    @ResponseBody
    public BaseResponse<PresentationRes> writePresentation(@PathVariable(name = "seminar-idx") Long seminarIdx,
                                                           @RequestPart("info") PostPresentationDto postPresentationDto,
                                                           @RequestPart(name = "image", required = false)MultipartFile multipartFile) {

        return new BaseResponse<>(adminProgramService.addPresentation(seminarIdx, postPresentationDto, multipartFile));
    }

    // 발표자료 수정
    @PatchMapping("/seminar/presentation")
    @ResponseBody
    public BaseResponse<PresentationRes> modifyPresentation(@RequestPart("info") PatchPresentationDto patchPresentationDto,
                                                            @RequestPart(name = "image", required = false)MultipartFile multipartFile) {

        return new BaseResponse<>(adminProgramService.modifyPresentation(patchPresentationDto, multipartFile));
    }

    // 발표자료 삭제
    @DeleteMapping("/seminar/presentation/{presentation-idx}")
    @ResponseBody
    public BaseResponse<PresentationRes> deletePresentation(@PathVariable(name = "presentation-idx") Long presentationIdx,
                                                       @RequestBody DeletePresentationDto deletePresentationDto) {

        return new BaseResponse<>(adminProgramService.deletePresentation(presentationIdx, deletePresentationDto));
    }

    // 프로그램 오픈
    @PatchMapping("/program/{program-idx}/open")
    @ResponseBody
    public BaseResponse<ProgramRes> openProgram(@PathVariable(name = "program-idx") Long programIdx) {

        return new BaseResponse<>(adminProgramService.openProgram(programIdx));
    }

}