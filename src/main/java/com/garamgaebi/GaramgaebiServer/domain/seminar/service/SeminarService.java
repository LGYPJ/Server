package com.garamgaebi.GaramgaebiServer.domain.seminar.service;

import com.garamgaebi.GaramgaebiServer.domain.seminar.dto.SeminarApplyReq;
import com.garamgaebi.GaramgaebiServer.domain.seminar.dto.SeminarCancelReq;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.UserSeminar;

public interface SeminarService {

    // 세미나 신청
    public void apply(SeminarApplyReq seminarApplyReq);
    // 세미나 신청 취소
    public void cancel(SeminarCancelReq seminarCancelReq);
    // 세미나 목록 조회
    public void findSeminarList();
    // 세미나 상세 페이지
    public void findSeminarPage(Long seminarIdx);
}
