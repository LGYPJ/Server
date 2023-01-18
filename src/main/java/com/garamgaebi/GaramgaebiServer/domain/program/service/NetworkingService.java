package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;

import java.util.List;

public interface NetworkingService {
    public ProgramDto findThisMonthNetworking();
    public ProgramDto findReadyNetworking();
    public List<ProgramDto> findClosedNetworking();
}
