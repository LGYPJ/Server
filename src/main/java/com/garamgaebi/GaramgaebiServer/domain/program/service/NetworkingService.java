package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.GetProgramListRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;

import java.util.List;

public interface NetworkingService {
    // 네트워킹 모아보기
    public GetProgramListRes findNetworkingCollectionList();
    // 홈 화면 네트워킹 리스트
    public List<ProgramDto> findMainNetworkingList();

}
