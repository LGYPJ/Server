package com.garamgaebi.GaramgaebiServer.domain.profile;

import com.garamgaebi.GaramgaebiServer.domain.profile.dto.PostCareerReq;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.PostEducationReq;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.PostQnaReq;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.PostSNSReq;
import com.garamgaebi.GaramgaebiServer.domain.profile.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
