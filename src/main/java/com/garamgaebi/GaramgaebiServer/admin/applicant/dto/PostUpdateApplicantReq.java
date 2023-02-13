package com.garamgaebi.GaramgaebiServer.admin.applicant.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.status.apply.ApplyStatus;
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
