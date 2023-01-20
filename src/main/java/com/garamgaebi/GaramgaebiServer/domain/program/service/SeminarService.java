package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SeminarService {
    // 이번달 세미나 조회
    public ProgramDto findThisMonthSeminar();
    // 예정된 세미나 조회
    public ProgramDto findReadySeminar();
    // 마감된 세미나 리스트 조회
    public List<ProgramDto> findClosedSeminarsList();
    // 홈 화면 세미나 리스트
    public List<ProgramDto> findMainSeminarList();
    // 세미나 상세 페이지
    public ProgramInfoDto findSeminarDetails(ProgramDetailReq programDetailReq);

    // 세미나 상세정보 발표자료 조회
    List<PresentationDto> findSeminarPresentationList(Long seminarIdx);
}
