package com.garamgaebi.GaramgaebiServer.admin.applicant;

import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.GetFindAllApplicantRes;
import com.garamgaebi.GaramgaebiServer.admin.applicant.service.AdminApplicantService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
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
     * Admin 프로그램 신청자 수정
     */
}
