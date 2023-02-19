package com.garamgaebi.GaramgaebiServer.domain.admin.applicant;

import com.garamgaebi.GaramgaebiServer.domain.admin.applicant.dto.GetFindAllApplicantRes;
import com.garamgaebi.GaramgaebiServer.domain.admin.applicant.dto.PostUpdateApplicantReq;
import com.garamgaebi.GaramgaebiServer.domain.admin.applicant.service.AdminApplicantService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/applicant")
@RequiredArgsConstructor
public class AdminApplicantController {
    private final AdminApplicantService service;

    /**
     * Admin 프로그램 신청자 조회
     * 이름 닉네임 전화번호 은행 계좌번호 입금여부 최종수정일자
     */
    @Operation(summary = "Admin 프로그램 신청자 조회")
    @GetMapping("/{programIdx}")
    @ResponseBody
    public BaseResponse<GetFindAllApplicantRes> findAllApplicant(@PathVariable long programIdx) {
        GetFindAllApplicantRes allApplicant = service.findAllApplicant(programIdx);
        return new BaseResponse<>(allApplicant);
    }

    /**
     * Admin 프로그램 신청자 상태 수정
     */
    @Operation(summary = "Admin 프로그램 신청 입금여부, 상태 수정")
    @PostMapping
    @ResponseBody
    public BaseResponse<Boolean> updateApplicant(@RequestBody PostUpdateApplicantReq req) {
        Boolean res = service.updateApplicant(req);
        return new BaseResponse<>(res);
    }
}
