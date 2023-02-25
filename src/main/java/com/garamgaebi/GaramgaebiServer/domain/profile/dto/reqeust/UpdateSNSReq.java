package com.garamgaebi.GaramgaebiServer.domain.profile.dto.reqeust;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateSNSReq {
    private Long snsIdx;
    private String address;
    private String type;
}
