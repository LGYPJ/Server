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
    public String saveQna(@RequestBody PostQnaReq req) {

        //문의 내용이 비어있는지 validation
        if (req.getContent().isEmpty() == true) {
            return "문의 내용이 비어있습니다.";
        }

        profileService.saveQna(req);
        return "문의 내용이 접수되었습니다.";

    }

    /**
     * POST SNS추가 API
     */
    @ResponseBody
    @PostMapping("/sns")
    public String saveSns(@RequestBody PostSNSReq req) {
        if (req.getAddress().isEmpty() == true) {
            return "비어있습니다.";
        }
        profileService.saveSns(req);
        return "SNS 등록 완료";
    }

    /**
     * POST 교육추가 API
     */
    @ResponseBody
    @PostMapping("/education")
    public String saveEducation(@RequestBody PostEducationReq req) {
        profileService.saveEducation(req);
        return "교육 등록 완료";
    }

    /**
     * POST 경력추가 API
     */
    @ResponseBody
    @PostMapping("/career")
    public String saveCareer(@RequestBody PostCareerReq req) {
        profileService.saveCareer(req);
        return "경력 등록 완료";
    }

    /**
     * GET 프로필 상세조회 API
     */
    @ResponseBody
    @GetMapping("/{memberIdx}")
    public GetProfileRes getProfile(@PathVariable long memberIdx) {
        GetProfileRes res = profileService.getProfile(memberIdx);
        return res;
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
    public String updateProfile(@RequestBody PostUpdateProfileReq req) {
        profileService.updateProfile(req);
        return "프로필 수정 완료";
    }
}
