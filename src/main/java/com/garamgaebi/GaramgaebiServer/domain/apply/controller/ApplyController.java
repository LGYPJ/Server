package com.garamgaebi.GaramgaebiServer.domain.apply.controller;

import com.garamgaebi.GaramgaebiServer.domain.apply.service.ApplyService;
import com.garamgaebi.GaramgaebiServer.domain.apply.dto.ApplyCancelDto;
import com.garamgaebi.GaramgaebiServer.domain.apply.dto.ApplyDto;
import com.garamgaebi.GaramgaebiServer.domain.apply.dto.GetApplyRes;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ApplyController", description = "프로그램 신청 및 취소 컨트롤러(담당자:줄리아)")
@RestController
@RequestMapping("/applies")
public class ApplyController {

    private final ApplyService applyService;

    @Autowired
    public ApplyController(ApplyService applyService) {this.applyService = applyService;}

    @Operation(summary = "프로그램 신청", description = "프로그램을 신청합니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "2001", description = "존재하지 않는 회원", content = @Content()),
            @ApiResponse(responseCode = "2002", description = "존재하지 않는 프로그램", content = @Content()),
            @ApiResponse(responseCode = "2015", description = "이미 신청한 프로그램", content = @Content()),
            @ApiResponse(responseCode = "2017", description = "오픈되지 않은 프로그램", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @PostMapping("/programs/enroll")
    public BaseResponse<Long> enroll(@RequestBody @Valid ApplyDto applyDto) {


        return new BaseResponse<>(applyService.enroll(applyDto));
    }

    @Operation(summary = "프로그램 신청 취소", description = "프로그램의 신청을 취소합니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "2001", description = "존재하지 않는 회원", content = @Content()),
            @ApiResponse(responseCode = "2002", description = "존재하지 않는 프로그램", content = @Content()),
            @ApiResponse(responseCode = "2016", description = "등록 상태가 아닙니다.", content = @Content()),
            @ApiResponse(responseCode = "2017", description = "오픈되지 않은 프로그램", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @PostMapping("/programs/leave")
    public BaseResponse<Long> leave(@RequestBody @Valid ApplyCancelDto applyCancelDto) {

        return new BaseResponse<>(applyService.leave(applyCancelDto));
    }

    @Operation(summary = "신청 정보 조회", description = "신청 당시 입력한 이름, 닉네임, 전화번호를 조회합니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "요청하신 페이지를 찾을 수 없습니다.", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @GetMapping("/{member-idx}/{program-idx}/info")
    public BaseResponse<GetApplyRes> getApplyInfo(@PathVariable("member-idx") Long memberIdx, @PathVariable("program-idx") Long programIdx) {

        return new BaseResponse<GetApplyRes>(applyService.findApplyInfo(memberIdx, programIdx));
    }
}


