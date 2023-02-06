package com.garamgaebi.GaramgaebiServer.admin.applicant.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.ApplyStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PostUpdateApplicantReq {
    private Long memberIdx;
    private String name;
    private String phone;
    private String deposit;
    private ApplyStatus status;
}
