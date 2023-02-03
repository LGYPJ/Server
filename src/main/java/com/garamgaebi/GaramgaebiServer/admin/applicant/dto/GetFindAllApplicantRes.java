package com.garamgaebi.GaramgaebiServer.admin.applicant.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetFindAllApplicantRes {
    private Long memberIdx;
    private String name;
    private String nickName;
    private String phone;
//    private ApplyStatus status;
}
