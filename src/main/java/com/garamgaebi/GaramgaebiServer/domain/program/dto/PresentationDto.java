package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PresentationDto {
    private Long presentationIdx;
    private String title;
    private String nickname;
    private String profileImgUrl;
    private String organization;
    private String content;
    private String presentationUrl;
}
