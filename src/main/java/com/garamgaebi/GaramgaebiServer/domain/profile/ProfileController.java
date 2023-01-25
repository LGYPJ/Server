package com.garamgaebi.GaramgaebiServer.domain.profile;

import com.garamgaebi.GaramgaebiServer.domain.profile.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.profile.service.ProfileService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/profile")
public class ProfileController {
    //--------------------------------------------------------//
    @Autowired
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    //--------------------------------------------------------//

    /**
     * POST 고객센터(QnA)신청 API
     */
    @Operation(summary = "Profile", description = "POST 고객센터(QnA)신청 API")
    @ResponseBody
    @PostMapping("/qna")
    public BaseResponse<Boolean> saveQna(@RequestBody PostQnaReq req) {

        //문의 내용이 비어있는지 validation
        if (req.getContent().isEmpty() == true) {
            return new BaseResponse<>(false);
        }
        profileService.saveQna(req);
        return new BaseResponse<>( true);
    }

    /**
     * POST SNS추가 API
     */
    @Operation(summary = "Profile", description = "POST SNS추가 API")
    @ResponseBody
    @PostMapping("/sns")
    public BaseResponse<Boolean> saveSns(@RequestBody PostSNSReq req) {
        if (req.getAddress().isEmpty() == true) {
            return new BaseResponse<>(false);
        }
        profileService.saveSns(req);
        return new BaseResponse<>(true);
    }

    /**
     * POST 교육추가 API
     */
    @Operation(summary = "Profile", description = "POST 교육추가 API")
    @ResponseBody
    @PostMapping("/education")
    public BaseResponse<Boolean> saveEducation(@RequestBody PostEducationReq req) {
        profileService.saveEducation(req);
        return new BaseResponse<>(true);
    }

    /**
     * POST 경력추가 API
     */
    @Operation(summary = "Profile", description = "POST 경력추가 API")
    @ResponseBody
    @PostMapping("/career")
    public BaseResponse<Boolean> saveCareer(@RequestBody PostCareerReq req) {
        profileService.saveCareer(req);
        return new BaseResponse<>(true);
    }

    /**
     * GET 프로필 조회 API
     */
    @Operation(summary = "Profile", description = "GET 프로필 조회 API")
    @ResponseBody
    @GetMapping("/{memberIdx}")
    public BaseResponse<GetProfileRes> getProfile(@PathVariable long memberIdx) {
        GetProfileRes res = profileService.getProfile(memberIdx);
        return new BaseResponse<>(res);
    }

    /**
     * GET 프로필 SNS 조회 API
     */
    @Operation(summary = "Profile", description = "GET 프로필 SNS 조회 API")
    @ResponseBody
    @GetMapping("/sns/{memberIdx}")
    public BaseResponse<List<GetSNSList>> getSNSList(@PathVariable long memberIdx) {
        List<GetSNSList> snsList =profileService.getSNSList(memberIdx);
        return new BaseResponse<>(snsList);
    }

    /**
     * GET 프로필 경력 조회 API
     */
    @Operation(summary = "Profile", description = "GET 프로필 경력 조회 API")
    @ResponseBody
    @GetMapping("/career/{memberIdx}")
    public BaseResponse<List<GetCareerList>> getCareerList(@PathVariable long memberIdx) {
        List<GetCareerList> careerList =profileService.getCareerList(memberIdx);
        return new BaseResponse<>(careerList);
    }

    /**
     * GET 프로필 교육 조회 API
     */
    @Operation(summary = "Profile", description = "GET 프로필 교육 조회 API")
    @ResponseBody
    @GetMapping("/education/{memberIdx}")
    public BaseResponse<List<GetEducationList>> getEducationList(@PathVariable long memberIdx) {
        List<GetEducationList> educationList =profileService.getEducationList(memberIdx);
        return new BaseResponse<>(educationList);
    }

    /**
     * GET 프로필 10명 추천 API
     * 10명 추천의 난수? 기준 정하기
     */
    @Operation(summary = "Profile", description = "GET 프로필 10명 추천 API")
    @ResponseBody
    @GetMapping("/profiles")
    public BaseResponse<List<GetProfilesRes>> getProfiles() {
        List<GetProfilesRes> resList = profileService.getProfiles();
        return new BaseResponse<>(resList);
    }

    /**
     * POST 프로필 수정 API
     */
    @Operation(summary = "Profile", description = "POST 프로필 수정 API")
    @ResponseBody
    @PostMapping("/edit/{memberIdx}")
    public BaseResponse<Boolean> updateProfile(@RequestBody PostUpdateProfileReq req) {
        profileService.updateProfile(req);
        return new BaseResponse<>(true);
    }
}
