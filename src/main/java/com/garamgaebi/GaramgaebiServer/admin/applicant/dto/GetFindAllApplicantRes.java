package com.garamgaebi.GaramgaebiServer.admin.applicant.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetFindAllApplicantRes {
    private List<ApplyList> applyList;
    private List<CancelList> cancelList;
}
