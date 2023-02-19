package com.garamgaebi.GaramgaebiServer.domain.admin.applicant.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateApplyList {
    private Long memberIdx;
    private String name;
    private Boolean status;
}
