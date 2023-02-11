package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;

import java.util.List;

public interface NetworkingService {
    // 이번달 세미나 조회
    public ProgramDto findThisMonthNetworking();
    // 예정된 세미나 조회
    public ProgramDto findReadyNetworking();
    // 마감된 세미나 리스트 조회
    public List<ProgramDto> findClosedNetworkingList();
    // 홈 화면 네트워킹 리스트
    public List<ProgramDto> findMainNetworkingList();
    // 네트워킹 상세 페이지
    public ProgramInfoDto findNetworkingDetails(Long networkingIdx, Long memberIdx);
    // 네트워킹 신청자 리스트 조회
    public List<ParticipantDto> findNetworkingParticipantsList(Long networkingIdx, Long memberIdx);

}
