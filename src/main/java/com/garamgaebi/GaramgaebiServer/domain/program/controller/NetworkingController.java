package com.garamgaebi.GaramgaebiServer.domain.program.controller;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.GetParticipantsRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramInfoDto;
import com.garamgaebi.GaramgaebiServer.domain.program.service.NetworkingService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.garamgaebi.GaramgaebiServer.global.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "NetworkingController", description = "네트워킹 컨트롤러(담당자:로니)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/networkings")
public class NetworkingController {

    private final NetworkingService networkingService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 이번 달 네트워킹 하나 조회
     * @Author 로니
     */
    @Operation(summary = "이번 달 네트워킹 조회", description = "이번 달에 있는 네트워킹을 한 건을 조회합니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @GetMapping("/this-month")
    public BaseResponse<ProgramDto> getThisMonthNetworking() { return new BaseResponse<>(networkingService.findThisMonthNetworking()); }


    /**
     * 예정된 네트워킹 중 가장 빠른 네트워킹 하나 조회
     * @Author 로니
     */
    @Operation(summary = "예정된 네트워킹 조회", description = "다음 달 이후 가장 최근에 있을 네트워킹을 한 건을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @GetMapping("/next-month")
    public BaseResponse<ProgramDto> getNextNetworking() {
        return new BaseResponse<>(networkingService.findReadyNetworking());
    }

    /**
     * 마감된 네트워킹을 리스트로 조회
     * @Author 로니
     */
    @Operation(summary = "마감된 네트워킹 리스트 조회", description = "마감된 네트워킹들을 리스트로 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @GetMapping("/closed")
    public BaseResponse<List<ProgramDto>> getClosedNetworkingList() {
        return new BaseResponse<>(networkingService.findClosedNetworkingList());
    }

    /**
     * 홈화면의 네트워킹 리스트를 하나의 리스트로 조회
     * @Author 로니
     */
    @Operation(summary = "홈 화면 네트워킹 조회", description = "모든 네트워킹 리스트를 이번 달/예정/마감 순서로 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @GetMapping("/main")
    public BaseResponse<List<ProgramDto>> getMainNetworkingList() {
        return new BaseResponse<>(networkingService.findMainNetworkingList());
    }


    /**
     * 네트워킹 상세페이지 윗 부분을 조회
     * @Author 로니
     */
    @Operation(summary = "네트워킹 상세정보 조회", description = "네트워킹 상세페이지 상단 상세 정보를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리스소", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content()),
            @ApiResponse(responseCode = "2001", description = "존재하지 않는 회원", content = @Content()),
            @ApiResponse(responseCode = "2014", description = "접근할 수 없는 프로그램입니다.", content = @Content())
    })
    @GetMapping("/{networking-idx}/info")
    public BaseResponse<ProgramInfoDto> getNetworkingDetailInfo(@PathVariable(name = "networking-idx") Long networkingIdx, @RequestParam("member-idx") Long memberIdx) {
        if(jwtTokenProvider.checkMemberIdx(memberIdx)) {
            new RestApiException(ErrorCode.NOT_AUTHORIZED_ACCESS);
        }

        return new BaseResponse<>(networkingService.findNetworkingDetails(networkingIdx, memberIdx));
    }


    /**
     * 네트워킹 상세페이지 신청자 리스트를 조회
     * @Author 로니
     */
    @Operation(summary = "네트워킹 신청자 리스트 조회", description = "네트워킹 신청자를 리스트로 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content()),
            @ApiResponse(responseCode = "2001", description = "존재하지 않는 회원", content = @Content()),
            @ApiResponse(responseCode = "2014", description = "접근할 수 없는 프로그램입니다.", content = @Content())
    })
    @GetMapping("/{networking-idx}/participants")
    public BaseResponse<GetParticipantsRes> getNetworkingParticipantList(@PathVariable(name = "networking-idx") Long networkingIdx, @RequestParam("member-idx") Long memberIdx) {
        if(jwtTokenProvider.checkMemberIdx(memberIdx)) {
            new RestApiException(ErrorCode.NOT_AUTHORIZED_ACCESS);
        }

        return new BaseResponse<>(networkingService.findNetworkingParticipantsList(networkingIdx, memberIdx));
    }

}