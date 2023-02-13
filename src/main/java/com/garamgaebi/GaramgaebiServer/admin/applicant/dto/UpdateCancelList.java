package com.garamgaebi.GaramgaebiServer.admin.applicant.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateCancelList {
    private Long memberIdx;
    private String name;
    private Boolean status;
}
