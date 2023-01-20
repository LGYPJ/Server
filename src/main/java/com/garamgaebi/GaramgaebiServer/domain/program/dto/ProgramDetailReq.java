package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProgramDetailReq {
    private Long memberIdx;
    private Long programIdx;
}
