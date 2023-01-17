package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;

import java.util.List;

public interface SeminarService {
    public ProgramDto findThisMonthSeminar();
    public ProgramDto findReadySeminar();
    public List<ProgramDto> findClosedSeminarList();

}
