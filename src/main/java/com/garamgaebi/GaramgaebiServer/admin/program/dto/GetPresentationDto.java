package com.garamgaebi.GaramgaebiServer.admin.program.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class GetPresentationDto {
    private Long idx;
    private String title;
    private String nickname;
    private String organization;
    private String content;
    private String presentationUrl;
    private String profileImg;
}
