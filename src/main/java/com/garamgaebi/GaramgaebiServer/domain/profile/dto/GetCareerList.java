package com.garamgaebi.GaramgaebiServer.domain.profile.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.IsWorking;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetCareerList {
    private Long careerIdx;
    private String company;
    private String position;
    private IsWorking isWorking;
    private Date startDate;
    private Date endDate;
}
