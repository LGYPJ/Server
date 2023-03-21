package com.garamgaebi.GaramgaebiServer.domain.admin.applicant.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetApplyList {
    private Long memberIdx;
    private String name;
    private String nickName;
    private String phone;
    private String bank;
    private String account;
    private Boolean status;
    private LocalDateTime updatedAt;
}
