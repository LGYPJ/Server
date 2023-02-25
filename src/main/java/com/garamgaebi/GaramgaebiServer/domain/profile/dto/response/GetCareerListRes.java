package com.garamgaebi.GaramgaebiServer.domain.profile.dto.response;

import com.garamgaebi.GaramgaebiServer.domain.profile.entity.vo.IsWorking;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetCareerListRes {
    private Long careerIdx;
    private String company;
    private String position;
    private IsWorking isWorking;
    private String startDate;
    private String endDate;
}
