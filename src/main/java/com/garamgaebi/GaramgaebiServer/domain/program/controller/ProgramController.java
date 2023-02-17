package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.service.ProgramService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "ProgramController", description = "프로그램 컨트롤러(담당자:로니)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;

    // 예정된 내 모임
    @Operation(summary = "예정된 내 모임 조회", description = "예정된 신청 프로그램을 리스트로 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/{member-idx}/ready")
    public BaseResponse<List<ProgramDto>> getMemberReadyProgramList(@PathVariable(name = "member-idx") Long memberIdx) {

        return new BaseResponse<>(programService.findMemberReadyProgramList(memberIdx));
    }

    // 지난 내 모임
    @Operation(summary = "지난 내 모임 조회", description = "지난 신청 프로그램을 리스트로 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @GetMapping("/{member-idx}/close")
    public BaseResponse<List<ProgramDto>> getMemberClsoedProgramList(@PathVariable(name = "member-idx") Long memberIdx) {

        return new BaseResponse<>(programService.findMemberClosedProgramList(memberIdx));
    }
}
