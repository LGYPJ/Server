package com.garamgaebi.GaramgaebiServer.admin.applicant;

import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.GetFindAllApplicantRes;
import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.PostUpdateApplicantReq;
import com.garamgaebi.GaramgaebiServer.admin.applicant.service.AdminApplicantService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/applicant")
@RequiredArgsConstructor
public class AdminApplicantController {
    private final AdminApplicantService service;

    /**
     * Admin 프로그램 신청자 조회
     * 프로그램idx -> 멤버인덱스 이름 닉네임 전화번호 입금여부체크 입금여부 상태
     * 너무 종류가 많은데??
     */
    @Operation(summary = "Admin 프로그램 신청자 조회")
    @GetMapping("/{programIdx}")
    @ResponseBody
    public BaseResponse<List<GetFindAllApplicantRes>> findAllApplicant(@PathVariable long programIdx) {
        List<GetFindAllApplicantRes> allApplicant = service.findAllApplicant(programIdx);
        return new BaseResponse<>(allApplicant);
    }

    /**
     * Admin 프로그램 신청자 상태 수정
     * 입금 여부에 대한 엔티티 상태 컬럼이 필요하다.
     * 로직 자체를 PM과 상의해봐야할듯...
     */
    @Operation(summary = "Admin 프로그램 신청 입금여부, 상태 수정")
    @PostMapping
    @ResponseBody
    public BaseResponse<String> updateApplicant(@RequestBody @Valid PostUpdateApplicantReq req) {
        String res = service.updateApplicant(req);
        return new BaseResponse<>(res);
    }
}
