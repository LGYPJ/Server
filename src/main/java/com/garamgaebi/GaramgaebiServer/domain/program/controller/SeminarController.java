package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.program.service.SeminarService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SeminarController", description = "세미나 컨트롤러(담당자:로니)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/seminars")
public class SeminarController {

    private final SeminarService seminarService;

    // 이번 달 세미나
    @Operation(summary = "이번 달 세미나 조회", description = "이번 달에 있는 세미나 한 건을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/this-month")
    public BaseResponse<ProgramDto> getThisMonthSeminar() {
        return new BaseResponse<>(seminarService.findThisMonthSeminar());
    }

    // 예정된 세미나
    @Operation(summary = "예정된 세미나 조회", description = "다음 달 이후 가장 최근에 있을 세미나 한 건을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/next-month")
    public BaseResponse<ProgramDto> getNextSeminar() {
        return new BaseResponse<>(seminarService.findReadySeminar());
    }

    // 마감된 세미나
    @Operation(summary = "마감된 세미나 조회", description = "마감된 세미나를 리스트로 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/closed")
    public BaseResponse<List<ProgramDto>> getClosedSeminarList() {
        return new BaseResponse<>(seminarService.findClosedSeminarsList());
    }

    //홈 화면 세미나 리스트
    @Operation(summary = "홈 화면 세미나 조회", description = "모든 세미나 리스트를 이번 달/예정/마감 순서로 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/main")
    public BaseResponse<List<ProgramDto>> getMainSeminarList() { return new BaseResponse<>(seminarService.findMainSeminarList()); }

    // 세미나 상세정보
    @Operation(summary = "세미나 상세정보 조회", description = "세미나 상세페이지 상단 부분인 세미나 상세정보를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "2001", description = "존재하지 않는 회원입니다.", content = @Content()),
            @ApiResponse(responseCode = "2002", description = "존재하지 않는 프로그램입니다.", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/info")
    public BaseResponse<ProgramInfoDto> getSeminarDetailInfo(@RequestBody @Valid ProgramDetailReq programDetailReq) {

        return new BaseResponse<>(seminarService.findSeminarDetails(programDetailReq));
    }

    // 세미나 신청자 리스트
    @Operation(summary = "세미나 신청자 리스트 조회", description = "세미나의 신청자를 리스트로 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/{seminar-idx}/participants")
    public BaseResponse<List<ParticipantDto>> getSeminarParticipantList(@PathVariable(name = "seminar-idx") Long seminarIdx) {

        return new BaseResponse<>(seminarService.findSeminarParticipantsList(seminarIdx));
    }

    // 세미나 발표 리스트
    @Operation(summary = "세미나 발표 리스트 조회", description = "세미나의 발표자료를 리스트로 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/{seminar-idx}/presentations")
    public BaseResponse<List<PresentationDto>> getSeminarPresentationList(@PathVariable(name = "seminar-idx") Long seminarIdx) {

        return new BaseResponse<>(seminarService.findSeminarPresentationList(seminarIdx));
    }

}
