package com.garamgaebi.GaramgaebiServer.domain.profile;

import com.garamgaebi.GaramgaebiServer.domain.profile.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.profile.service.ProfileService;
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
    @ResponseBody
    @PostMapping("/qna")
    public Boolean saveQna(@RequestBody PostQnaReq req) {

        //문의 내용이 비어있는지 validation
        if (req.getContent().isEmpty() == true) {
            return false;
        }
        profileService.saveQna(req);
        return true;
    }

    /**
     * POST SNS추가 API
     */
    @ResponseBody
    @PostMapping("/sns")
    public Boolean saveSns(@RequestBody PostSNSReq req) {
        if (req.getAddress().isEmpty() == true) {
            return false;
        }
        profileService.saveSns(req);
        return true;
    }

    /**
     * POST 교육추가 API
     */
    @ResponseBody
    @PostMapping("/education")
    public Boolean saveEducation(@RequestBody PostEducationReq req) {
        profileService.saveEducation(req);
        return true;
    }

    /**
     * POST 경력추가 API
     */
    @ResponseBody
    @PostMapping("/career")
    public Boolean saveCareer(@RequestBody PostCareerReq req) {
        profileService.saveCareer(req);
        return true;
    }

    /**
     * GET 프로필 조회 API
     */
    @ResponseBody
    @GetMapping("/{memberIdx}")
    public GetProfileRes getProfile(@PathVariable long memberIdx) {
        GetProfileRes res = profileService.getProfile(memberIdx);
        return res;
    }

    /**
     * GET 프로필 SNS 조회 API
     */
    @ResponseBody
    @GetMapping("/sns/{memberIdx}")
    public List<GetSNSList> getSNSList(@PathVariable long memberIdx) {
        List<GetSNSList> snsList =profileService.getSNSList(memberIdx);
        return snsList;
    }

    /**
     * GET 프로필 경력 조회 API
     */
    @ResponseBody
    @GetMapping("/career/{memberIdx}")
    public List<GetCareerList> getCareerList(@PathVariable long memberIdx) {
        List<GetCareerList> careerList =profileService.getCareerList(memberIdx);
        return careerList;
    }

    /**
     * GET 프로필 교육 조회 API
     */
    @ResponseBody
    @GetMapping("/education/{memberIdx}")
    public List<GetEducationList> getEducationList(@PathVariable long memberIdx) {
        List<GetEducationList> educationList =profileService.getEducationList(memberIdx);
        return educationList;
    }

    /**
     * GET 프로필 10명 추천 API
     * 10명 추천의 난수? 기준 정하기
     */
    @ResponseBody
    @GetMapping("/profiles")
    public List<GetProfilesRes> getProfiles() {
        List<GetProfilesRes> resList = profileService.getProfiles();
        return resList;
    }

    /**
     * POST 프로필 수정 API
     */
    @ResponseBody
    @PostMapping("/edit/{memberIdx}")
    public Boolean updateProfile(@RequestBody PostUpdateProfileReq req) {
        profileService.updateProfile(req);
        return true;
    }
}
