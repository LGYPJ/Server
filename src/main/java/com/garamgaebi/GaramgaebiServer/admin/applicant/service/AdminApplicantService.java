package com.garamgaebi.GaramgaebiServer.admin.applicant.service;

import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.GetApplyList;
import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.GetCancelList;
import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.GetFindAllApplicantRes;
import com.garamgaebi.GaramgaebiServer.admin.applicant.dto.PostUpdateApplicantReq;
import com.garamgaebi.GaramgaebiServer.admin.applicant.repository.AdminApplicantRepository;
import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.ApplyConfirmEvent;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.NonDepositCancelEvent;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.RefundEvent;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


import static com.garamgaebi.GaramgaebiServer.domain.entity.status.apply.ApplyStatus.*;
import static com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus.CLOSED_CONFIRM;
import static com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode.NOT_EXIST_SEMINAR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AdminApplicantService {
    private final AdminApplicantRepository repository;

    // 이벤트 퍼블리셔
    private final ApplicationEventPublisher publisher;

    /**
     * Admin 프로그램 신청자 조회
     */
    @Transactional
    public GetFindAllApplicantRes findAllApplicant(long programIdx) {
        Program program = repository.findProgram(programIdx);
        if (program == null) {
            log.info("Program NOT EXIST : {}", programIdx);
            throw new RestApiException(NOT_EXIST_SEMINAR);
        }
        GetFindAllApplicantRes res = new GetFindAllApplicantRes();
        List<GetApplyList> aList = new ArrayList<>();
        List<GetCancelList> cList = new ArrayList<>();

        //신청자 조회
        List<Apply> applyList = repository.findApplyList(programIdx);
        //신청자 입금상태 설정 및 리스트에 추가
        for (int i = 0; i < applyList.size(); i++) {
            GetApplyList a = new GetApplyList();
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
            GetCancelList c = new GetCancelList();
            c.setMemberIdx(cancelList.get(i).getMember().getMemberIdx());
            c.setName(cancelList.get(i).getName());
            c.setNickName(cancelList.get(i).getNickname());
            c.setPhone(cancelList.get(i).getPhone());
            c.setBank(cancelList.get(i).getBank());
            c.setAccount(cancelList.get(i).getAccount());
            if (cancelList.get(i).getStatus().toString().equals("CANCEL_REFUND")) {
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
    public Boolean updateApplicant(PostUpdateApplicantReq req) {
        //true(체크) -> false(체크안함) // 상태 APPLY 로 바꿈
        //false(체크안함) -> true(체크) // 상태 CANCEL 로 바꿈
        Program programCheck = repository.findProgram(req.getProgramIdx());
        if (programCheck == null) {
            log.info("Program NOT EXIST : {}", req.getProgramIdx());
            throw new RestApiException(NOT_EXIST_SEMINAR);
        }

        List<Apply> applyConfirm = new ArrayList<Apply>();
        List<Apply> nonDeposit = new ArrayList<Apply>();
        List<Apply> refund = new ArrayList<Apply>();

        //신청자
        for (int i = 0; i < req.getApplyList().size(); i++) {
            if (req.getApplyList().get(i).getStatus().equals(Boolean.TRUE)) {
                Apply apply = repository.findOneProgramApply(req.getApplyList().get(i).getMemberIdx(),req.getProgramIdx());
                if(apply.getStatus() != APPLY_CONFIRM) {
                    applyConfirm.add(apply);
                }
                apply.setStatus(APPLY_CONFIRM);
            }
            if (req.getApplyList().get(i).getStatus().equals(Boolean.FALSE)) {
                Apply apply = repository.findOneProgramApply(req.getApplyList().get(i).getMemberIdx(),req.getProgramIdx());
                if(apply.getStatus() != APPLY_CANCEL) {
                    nonDeposit.add(apply);
                }
                apply.setStatus(APPLY_CANCEL);
            }
        }

        //취소자
        for (int i = 0; i < req.getCancelList().size(); i++) {
            if (req.getCancelList().get(i).getStatus().equals(Boolean.TRUE)) {
                Apply cancel = repository.findOneProgramApply(req.getCancelList().get(i).getMemberIdx(),req.getProgramIdx());
                if(cancel.getStatus() != CANCEL_REFUND) {
                    refund.add(cancel);
                }
                cancel.setStatus(CANCEL_REFUND);
            }
            if (req.getCancelList().get(i).getStatus().equals(Boolean.FALSE)) {
                Apply cancel = repository.findOneProgramApply(req.getCancelList().get(i).getMemberIdx(),req.getProgramIdx());
                cancel.setStatus(CANCEL_CONFIRM); // 주의!
            }
        }

        //프로그램 상태 마감
        Program program = repository.findProgram(req.getProgramIdx());
        program.setStatus(CLOSED_CONFIRM);

        // 알림 발송
        if(!applyConfirm.isEmpty()) {
            publisher.publishEvent(new ApplyConfirmEvent(applyConfirm, program));
        }
        if(!nonDeposit.isEmpty()) {
            publisher.publishEvent(new NonDepositCancelEvent(nonDeposit, program));
        }
        if(!refund.isEmpty()) {
            publisher.publishEvent(new RefundEvent(refund, program));
        }

        return true;
    }
}
