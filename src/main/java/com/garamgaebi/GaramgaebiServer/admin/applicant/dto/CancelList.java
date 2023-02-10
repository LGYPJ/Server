package com.garamgaebi.GaramgaebiServer.admin.applicant.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CancelList {
    private Long memberIdx;
    private String name;
    private String nickName;
    private String phone;
    private String bank;
    private String account;
    private Boolean status;
    private LocalDateTime updatedAt;
}
