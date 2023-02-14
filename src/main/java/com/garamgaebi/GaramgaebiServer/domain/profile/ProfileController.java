package com.garamgaebi.GaramgaebiServer.domain.profile;

import com.garamgaebi.GaramgaebiServer.domain.profile.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.profile.service.ProfileService;
import com.garamgaebi.GaramgaebiServer.global.util.S3Uploader;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "ProfileController", description = "프로필 컨트롤러(담당자:래리)")
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
    @Operation(summary = "POST 고객센터(QnA)신청 API", description = "유저 QNA 추가")
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
    @Operation(summary = "POST SNS추가 API", description = "유저 SNS 추가")
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
     * PATCH SNS 수정
     */
    @Operation(summary = "PATCH sns 수정")
    @ResponseBody
    @PatchMapping("/sns")
    public BaseResponse<Boolean> updateSNS(@RequestBody UpdateSNSReq req) {
        Boolean res = profileService.updateSNS(req);
        return new BaseResponse<>(res);
    }

    /**
     * Delete SNS 삭제
     */
    @Operation(summary = "Delete SNS 삭제")
    @ResponseBody
    @DeleteMapping("/sns/{snsIdx}")
    public BaseResponse<Boolean> deleteSNS(@PathVariable long snsIdx) {
        Boolean res = profileService.deleteSNS(snsIdx);
        return new BaseResponse<>(res);
    }

    /**
     * POST 교육추가 API
     */
    @Operation(summary = "POST 교육추가 API", description = "유저 교육 추가")
    @ResponseBody
    @PostMapping("/education")
    public BaseResponse<Boolean> saveEducation(@RequestBody PostEducationReq req) {
        profileService.saveEducation(req);
        return new BaseResponse<>(true);
    }

    /**
     * PATCH 교육 수정
     */
    @Operation(summary = "PATCH 교육 수정")
    @ResponseBody
    @PatchMapping("/education")
    public BaseResponse<Boolean> updateEducation(@RequestBody UpdateEducationReq req) {
        Boolean res = profileService.updateEducation(req);
        return new BaseResponse<>(res);
    }

    /**
     * Delete 교육 삭제
     */
    @Operation(summary = "Delete 교육 삭제")
    @ResponseBody
    @DeleteMapping("/education/{educationIdx}")
    public BaseResponse<Boolean> deleteEducation(@PathVariable long educationIdx) {
        Boolean res = profileService.deleteEducation(educationIdx);
        return new BaseResponse<>(res);
    }

    /**
     * POST 경력추가 API
     */
    @Operation(summary = "POST 경력추가 API", description = "유저 경력 추가")
    @ResponseBody
    @PostMapping("/career")
    public BaseResponse<Boolean> saveCareer(@RequestBody PostCareerReq req) {
        profileService.saveCareer(req);
        return new BaseResponse<>(true);
    }

    /**
     * PATCH 경력 수정
     */
    @Operation(summary = "PATCH 경력 수정")
    @ResponseBody
    @PatchMapping("/career")
    public BaseResponse<Boolean> updateCareer(@RequestBody UpdateCareerReq req) {
        Boolean res = profileService.updateCareer(req);
        return new BaseResponse<>(res);
    }

    /**
     * Delete 경력 삭제
     */
    @Operation(summary = "Delete 경력 삭제")
    @ResponseBody
    @DeleteMapping("/career/{careerIdx}")
    public BaseResponse<Boolean> deleteCareer(@PathVariable long careerIdx) {
        Boolean res = profileService.deleteCareer(careerIdx);
        return new BaseResponse<>(res);
    }

    /**
     * GET 프로필 조회 API
     */
    @Operation(summary = "GET 프로필 조회 API", description = "유저프로필 조회")
    @ResponseBody
    @GetMapping("/{memberIdx}")
    public BaseResponse<GetProfileRes> getProfile(@PathVariable long memberIdx) {
        GetProfileRes res = profileService.getProfile(memberIdx);
        return new BaseResponse<>(res);
    }

    /**
     * GET 프로필 SNS 조회 API
     */
    @Operation(summary = "GET 프로필 SNS 조회 API", description = "유저프로필 SNS 리스트조회")
    @ResponseBody
    @GetMapping("/sns/{memberIdx}")
    public BaseResponse<List<GetSNSList>> getSNSList(@PathVariable long memberIdx) {
        List<GetSNSList> snsList =profileService.getSNSList(memberIdx);
        return new BaseResponse<>(snsList);
    }

    /**
     * GET 프로필 경력 조회 API
     */
    @Operation(summary = "GET 프로필 경력 조회 API", description = "유저프로필 경력 리스트조회")
    @ResponseBody
    @GetMapping("/career/{memberIdx}")
    public BaseResponse<List<GetCareerList>> getCareerList(@PathVariable long memberIdx) {
        List<GetCareerList> careerList =profileService.getCareerList(memberIdx);
        return new BaseResponse<>(careerList);
    }

    /**
     * GET 프로필 교육 조회 API
     */
    @Operation(summary = "GET 프로필 교육 조회 API", description = "유저프로필 교육 리스트조회")
    @ResponseBody
    @GetMapping("/education/{memberIdx}")
    public BaseResponse<List<GetEducationList>> getEducationList(@PathVariable long memberIdx) {
        List<GetEducationList> educationList =profileService.getEducationList(memberIdx);
        return new BaseResponse<>(educationList);
    }

    /**
     * GET 프로필 11명 추천 API
     */
    @Operation(summary = "GET 프로필 11명 추천 API", description = "유저프로필 11명 리스트추천")
    @ResponseBody
    @GetMapping("/profiles")
    public BaseResponse<List<GetProfilesRes>> getProfiles() {
        List<GetProfilesRes> resList = profileService.getProfiles();
        return new BaseResponse<>(resList);
    }

    /**
     * POST 프로필 수정 API
     */
    @Operation(summary = "POST 프로필 수정 API", description = "유저프로필 수정")
    @ResponseBody
    @PostMapping("/edit")
    public BaseResponse<ProfileRes> updateProfile(@RequestPart("info") PostUpdateProfileReq req, @RequestPart(name = "image", required = false) MultipartFile multipartFile) {

        return new BaseResponse<>(new ProfileRes(profileService.updateProfile(req, multipartFile)));
    }

    /**
     * 이미지 저장 API

    @Operation(summary = "POST 프로필 사진 저장/수정 (래리/최준현)", description= "유저프로필 사진 저장")
    @PostMapping("/images")
    public BaseResponse<Boolean> imageProfile(@RequestPart("info") S3Profile profile, @RequestPart("image") MultipartFile multipartFile)
            throws IOException {

        // S3Uploader.upload(업로드 할 이미지 파일, S3 디렉토리명) : S3에 저장된 이미지의 주소(url) 반환
        String profileUrl = s3Uploader.upload(multipartFile, "profile");
        boolean req = profileService.imageProfile(profile, profileUrl);
        return new BaseResponse<>(req);
    }
     */

}
