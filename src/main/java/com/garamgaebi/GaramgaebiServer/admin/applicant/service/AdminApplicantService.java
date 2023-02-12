package com.garamgaebi.GaramgaebiServer.admin.applicant.service;

import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.ApplyList;
import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.CancelList;
import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.GetFindAllApplicantRes;
import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.PostUpdateApplicantReq;
import com.garamgaebi.GaramgaebiServer.admin.applicant.repository.AdminApplicantRepository;
import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


import static com.garamgaebi.GaramgaebiServer.domain.entity.status.apply.ApplyStatus.APPLY_CONFIRM;
import static com.garamgaebi.GaramgaebiServer.domain.entity.status.apply.ApplyStatus.CANCEL_CONFIRM;
import static com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode.NOT_EXIST_SEMINAR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminApplicantService {
    private final AdminApplicantRepository repository;

    /**
     * Admin 프로그램 신청자 조회
     */
    @Transactional
    public GetFindAllApplicantRes findAllApplicant(long programIdx) {
        Program program = repository.findProgram(programIdx);
        if (program == null) {
            throw new RestApiException(NOT_EXIST_SEMINAR);
        }
        GetFindAllApplicantRes res = new GetFindAllApplicantRes();
        List<ApplyList> aList = new ArrayList<>();
        List<CancelList> cList = new ArrayList<>();

        //신청자 조회
        List<Apply> applyList = repository.findApplyList(programIdx);
        //신청자 입금상태 설정 및 리스트에 추가
        for (int i = 0; i < applyList.size(); i++) {
            ApplyList a = new ApplyList();
            a.setMemberIdx(applyList.get(i).getMember().getMemberIdx());
            a.setName(applyList.get(i).getName());
            a.setNickName(applyList.get(i).getNickname());
            a.setPhone(applyList.get(i).getPhone());
            a.setBank(applyList.get(i).getBank());
            a.setAccount(applyList.get(i).getAccount());
            System.out.println(applyList.get(i).getStatus());
            if (applyList.get(i).getStatus().toString().equals("APPLY_CONFIRM")) {
                a.setStatus(true);
            } else {
                a.setStatus(false);
            }
            a.setUpdatedAt(applyList.get(i).getUpdatedAt());
            aList.add(a);
        }
        //취소자 조회
        List<Apply> cancelList = repository.findCancelList(programIdx);
        //취소자 환불상태 설정 및 리스트에 추가
        for (int i = 0; i < cancelList.size(); i++) {
            CancelList c = new CancelList();
            c.setMemberIdx(cancelList.get(i).getMember().getMemberIdx());
            c.setName(cancelList.get(i).getName());
            c.setNickName(cancelList.get(i).getNickname());
            c.setPhone(cancelList.get(i).getPhone());
            c.setBank(cancelList.get(i).getBank());
            c.setAccount(cancelList.get(i).getAccount());
            if (cancelList.get(i).getStatus().toString().equals("CANCEL_REFUND") ||
                    cancelList.get(i).getStatus().toString().equals("CANCEL_CONFIRM")) {
                c.setStatus(true);
            } else {
                c.setStatus(false);
            }
            c.setUpdatedAt(cancelList.get(i).getUpdatedAt());
            cList.add(c);
        }
        res.setApplyList(aList);
        res.setCancelList(cList);
        return res;
    }

    /**
     * Admin 프로그램 신청자 상태 수정
     */
    @Transactional
    public String updateApplicant(PostUpdateApplicantReq req) {
        int applyTrue = 0;
        for (int i = 0; i < req.getApplyList().size(); i++) {
            Boolean status = req.getApplyList().get(i).getStatus();
            //상태값이 모두 트루인지 확인
            if (status.equals(Boolean.FALSE)) {
                applyTrue = 1;
            }
            Apply apply = repository.findOneProgramApply(req.getApplyList().get(i).getMemberIdx(), req.getProgramIdx());
            if (status.equals(Boolean.TRUE)) {
                apply.setStatus(APPLY_CONFIRM);
            }
        }
        int cancelTrue = 0;
        for (int i = 0; i < req.getCancelList().size(); i++) {
            Boolean status = req.getCancelList().get(i).getStatus();
            //상태값이 모두 트루인지 확인
            if (status.equals(Boolean.FALSE)) {
                cancelTrue = 1;
            }
            Apply apply = repository.findOneProgramApply(req.getCancelList().get(i).getMemberIdx(), req.getProgramIdx());
            if (status.equals(Boolean.TRUE)) {
                apply.setStatus(CANCEL_CONFIRM);
            }
        }

        //모든 상태가 체크표시 되어있기에 프로그램 상태를 변환
        if (applyTrue == 0 && cancelTrue == 0) {

        }
        return "수정을 완료하였습니다.";
    }
}
