package com.garamgaebi.GaramgaebiServer.domain.seminar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SeminarApplyReq {
    private Long userIdx;
    private Long seminarIdx;
    private String phoneNumber;
}
