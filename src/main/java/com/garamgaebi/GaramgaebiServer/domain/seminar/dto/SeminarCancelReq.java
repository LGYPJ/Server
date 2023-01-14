package com.garamgaebi.GaramgaebiServer.domain.seminar.dto;

import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.UserSeminar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeminarCancelReq {
    private Long userSeminarIdx;
    private String bank;
    private String account;
}