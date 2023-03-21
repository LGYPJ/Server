package com.garamgaebi.GaramgaebiServer.domain.profile.dto.reqeust;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class UpdateEducationReq {
    private Long educationIdx;
    private String institution;
    private String major;
    private String isLearning;
    private String startDate;
    private String endDate;
}
