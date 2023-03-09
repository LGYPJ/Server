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
public class PostEducationReq {
    private Long memberIdx;
    private String institution;
    private String major;
    private String isLearning;
    private String startDate;
    private String endDate;
}
