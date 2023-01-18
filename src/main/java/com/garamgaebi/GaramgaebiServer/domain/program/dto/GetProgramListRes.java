package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class GetProgramListRes {
    private ProgramDto thisMonthProgram;
    private ProgramDto nextProgram;
    private List<ProgramDto> closedProgram;
}
