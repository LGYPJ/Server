package com.garamgaebi.GaramgaebiServer.domain.admin.applicant.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetFindAllApplicantRes {
    private List<GetApplyList> applyList;
    private List<GetCancelList> cancelList;
}
