package com.garamgaebi.GaramgaebiServer.domain.profile.dto.response;

import com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsLearning;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class GetEducationListRes {
    private Long educationIdx;
    private String institution;
    private String major;
    private IsLearning isLearning;
    private String startDate;
    private String endDate;
}
