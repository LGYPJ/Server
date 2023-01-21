package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;

import java.util.List;

public interface ProgramService {
    // 예정된 내 모임 리스트 조회
    List<ProgramDto> findMemberReadyProgramList(Long memberIdx);

    // 지난 내 모임 리스트 조회
    List<ProgramDto> findMemberClosedProgramList(Long memberIdx);
}
