package com.garamgaebi.GaramgaebiServer.domain.admin.applicant.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PostUpdateApplicantReq {
    private long programIdx;
    private List<UpdateApplyList> applyList;
    private List<UpdateCancelList> cancelList;
}
