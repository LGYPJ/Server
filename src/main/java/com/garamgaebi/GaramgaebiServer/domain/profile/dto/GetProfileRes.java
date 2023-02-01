package com.garamgaebi.GaramgaebiServer.domain.profile.dto;

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
    private String content;
    private String profileUrl;
}
