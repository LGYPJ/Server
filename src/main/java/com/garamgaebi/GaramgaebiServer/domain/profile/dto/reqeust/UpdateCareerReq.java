package com.garamgaebi.GaramgaebiServer.domain.profile.dto.reqeust;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateCareerReq {
    private Long careerIdx;
    private String company;
    private String position;
    private String isWorking;
    private String startDate;
    private String endDate;
}
