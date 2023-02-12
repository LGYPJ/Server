package com.garamgaebi.GaramgaebiServer.domain.profile.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateEducationReq {
    private Long educationIdx;
    private String institution;
    private String major;
    private String isLearning;
    private String startDate;
    private String endDate;
}
