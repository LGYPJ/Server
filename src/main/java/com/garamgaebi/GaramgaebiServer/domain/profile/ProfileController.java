package com.garamgaebi.GaramgaebiServer.domain.profile;

import com.garamgaebi.GaramgaebiServer.domain.S3TestMemberDto;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.profile.service.ProfileService;
import com.garamgaebi.GaramgaebiServer.global.S3Uploader;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/profile")
public class ProfileController {
    //--------------------------------------------------------//
    @Autowired
    private final ProfileService profileService;
    @Autowired
    private final S3Uploader s3Uploader;

    public ProfileController(ProfileService profileService, S3Uploader s3Uploader) {
        this.profileService = profileService;
        this.s3Uploader = s3Uploader;
    }
    //--------------------------------------------------------//

    /**
     * POST 고객센터(QnA)신청 API
     */
    @Operation(summary = "POST 고객센터(QnA)신청 API (래리/최준현)", description = "{\n" +
            "    \"memberIdx\":\"1\",\n" +
            "    \"email\":\"email@example.com\",\n" +
            "    \"category\":\"문의문의9\",\n" +
            "    \"content\":\"문의내용\"\n" +
            "}")
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
    @Operation(summary = "POST SNS추가 API (래리/최준현)", description = "{\n" +
            "    \"memberIdx\":\"1\",\n" +
            "    \"address\":\"exampleinstagram.com\"\n" +
            "}")
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
    @Operation(summary = "POST 교육추가 API (래리/최준현)", description = "{\n" +
            "    \"memberIdx\" : \"1\",\n" +
            "    \"institution\" : \"가천대학교\",\n" +
            "    \"major\" : \"소프트웨어학과\",\n" +
            "    \"isLearning\" : \"TRUE\",\n" +
            "    \"startDate\" : \"2020-03\",\n" +
            "    \"endDate\" : \"\"\n" +
            "}\n" +
            "*isLearning -> TRUE, FALSE")
    @ResponseBody
    @PostMapping("/education")
    public BaseResponse<Boolean> saveEducation(@RequestBody PostEducationReq req) {
        profileService.saveEducation(req);
        return new BaseResponse<>(true);
    }

    /**
     * POST 경력추가 API
     */
    @Operation(summary = "POST 경력추가 API (래리/최준현)", description = "{\n" +
            "    \"memberIdx\" : \"1\",\n" +
            "    \"company\" : \"라인\",\n" +
            "    \"position\" : \"풀스택\",\n" +
            "    \"isWorking\" : \"FALSE\",\n" +
            "    \"startDate\" : \"2022-06\",\n" +
            "    \"endDate\" : \"2023-09\"\n" +
            "}\n" +
            "*isWorking -> TRUE, FALSE")
    @ResponseBody
    @PostMapping("/career")
    public BaseResponse<Boolean> saveCareer(@RequestBody PostCareerReq req) {
        profileService.saveCareer(req);
        return new BaseResponse<>(true);
    }

    /**
     * GET 프로필 조회 API
     */
    @Operation(summary = "GET 프로필 조회 API (래리/최준현)", description = "유저프로필 조회")
    @ResponseBody
    @GetMapping("/{memberIdx}")
    public BaseResponse<GetProfileRes> getProfile(@PathVariable long memberIdx) {
        GetProfileRes res = profileService.getProfile(memberIdx);
        return new BaseResponse<>(res);
    }

    /**
     * GET 프로필 SNS 조회 API
     */
    @Operation(summary = "GET 프로필 SNS 조회 API (래리/최준현)", description = "유저프로필 SNS 리스트조회")
    @ResponseBody
    @GetMapping("/sns/{memberIdx}")
    public BaseResponse<List<GetSNSList>> getSNSList(@PathVariable long memberIdx) {
        List<GetSNSList> snsList =profileService.getSNSList(memberIdx);
        return new BaseResponse<>(snsList);
    }

    /**
     * GET 프로필 경력 조회 API
     */
    @Operation(summary = "GET 프로필 경력 조회 API (래리/최준현)", description = "유저프로필 경력 리스트조회")
    @ResponseBody
    @GetMapping("/career/{memberIdx}")
    public BaseResponse<List<GetCareerList>> getCareerList(@PathVariable long memberIdx) {
        List<GetCareerList> careerList =profileService.getCareerList(memberIdx);
        return new BaseResponse<>(careerList);
    }

    /**
     * GET 프로필 교육 조회 API
     */
    @Operation(summary = "GET 프로필 교육 조회 API (래리/최준현)", description = "유저프로필 교육 리스트조회")
    @ResponseBody
    @GetMapping("/education/{memberIdx}")
    public BaseResponse<List<GetEducationList>> getEducationList(@PathVariable long memberIdx) {
        List<GetEducationList> educationList =profileService.getEducationList(memberIdx);
        return new BaseResponse<>(educationList);
    }

    /**
     * GET 프로필 11명 추천 API
     */
    @Operation(summary = "GET 프로필 10명 추천 API (래리/최준현)", description = "유저프로필 10명 리스트추천")
    @ResponseBody
    @GetMapping("/profiles")
    public BaseResponse<List<GetProfilesRes>> getProfiles() {
        List<GetProfilesRes> resList = profileService.getProfiles();
        return new BaseResponse<>(resList);
    }

    /**
     * POST 프로필 수정 API
     */
    @Operation(summary = "POST 프로필 수정 API (래리/최준현)", description = "유저프로필 수정")
    @ResponseBody
    @PostMapping("/edit/{memberIdx}")
    public BaseResponse<Boolean> updateProfile(@RequestBody PostUpdateProfileReq req) {
        profileService.updateProfile(req);
        return new BaseResponse<>(true);
    }

    /**
     * 이미지 저장 API
     */
    @Operation(summary = "POST 프로필 사진 저장/수정 (래리/최준현)", description= "유저프로필 사진 저장")
    @PostMapping("/images")
    public BaseResponse<Boolean> imageProfile(@RequestPart("info") S3Profile profile, @RequestPart("image") MultipartFile multipartFile)
            throws IOException {

        // S3Uploader.upload(업로드 할 이미지 파일, S3 디렉토리명) : S3에 저장된 이미지의 주소(url) 반환
        String profileUrl = s3Uploader.upload(multipartFile, "/S3resource/profile");
        boolean req = profileService.imageProfile(profile, profileUrl);
        return new BaseResponse<>(req);
    }

}
