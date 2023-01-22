package com.garamgaebi.GaramgaebiServer.domain.profile.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.Career;
import com.garamgaebi.GaramgaebiServer.domain.entity.Education;
import com.garamgaebi.GaramgaebiServer.domain.entity.SNS;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetProfileRes {
    private Long memberIdx;
    private String nickName;
    private String profileEmail;
    private String belong;
    private String belong2;
    private String content;
    private List<SNS> SNSs;
    private List<Career> careers;
    private List<Education> educations;
}
