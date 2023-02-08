package com.garamgaebi.GaramgaebiServer.admin.applicant.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ApplyList {
    private Long memberIdx;
    private String name;
    private String nickName;
    private String phone;
    private String bank;
    private String account;
    private Boolean status;
//    private String date;
}
