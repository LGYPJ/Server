package com.garamgaebi.GaramgaebiServer.domain.profile.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class GetSNSListRes {
    private Long snsIdx;
    private String address;
    private String type;

}
