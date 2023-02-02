package com.garamgaebi.GaramgaebiServer.admin.applicant.service;

import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.GetFindAllApplicantRes;
import com.garamgaebi.GaramgaebiServer.admin.applicant.repository.AdminApplicantRepository;
import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminApplicantService {
    private final AdminApplicantRepository repository;

    /**
     * Admin 프로그램 신청자 조회
     */
    @Transactional
    public List<GetFindAllApplicantRes> findAllApplicant(long programIdx) {
        List<Apply> applies = repository.findAllApplicant(programIdx);
        List<GetFindAllApplicantRes> resList = new ArrayList<>();
        for (int i = 0; i < applies.size(); i++) {
            GetFindAllApplicantRes res = new GetFindAllApplicantRes();
            res.setMemberIdx(applies.get(i).getMember().getMemberIdx());
            res.setName(applies.get(i).getName());
            res.setNickName(applies.get(i).getNickname());
            res.setPhone(applies.get(i).getPhone());
            resList.add(res);
        }
        return resList;

    }
}
