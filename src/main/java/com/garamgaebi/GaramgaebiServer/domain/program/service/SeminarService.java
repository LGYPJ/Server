package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.GetProgramListRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;

import java.util.List;

public interface SeminarService {
    // 세미나 모아보기
    public GetProgramListRes findSeminarCollectionList();
    // 홈 화면 세미나 리스트
    public List<ProgramDto> findMainSeminarList();

}
