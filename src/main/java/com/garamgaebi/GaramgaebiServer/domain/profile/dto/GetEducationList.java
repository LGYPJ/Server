package com.garamgaebi.GaramgaebiServer.domain.profile.dto;

import com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsLearning;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetEducationList {
    private Long educationIdx;
    private String institution;
    private String major;
    private IsLearning isLearning;
    private String startDate;
    private String endDate;
}
