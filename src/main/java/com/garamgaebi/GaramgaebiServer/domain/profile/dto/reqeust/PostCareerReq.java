package com.garamgaebi.GaramgaebiServer.domain.profile.dto.reqeust;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostCareerReq {
    private Long memberIdx;
    private String company;
    private String position;
    private String isWorking;
    private String startDate;
    private String endDate;
}
