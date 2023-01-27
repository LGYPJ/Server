package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "발표자료 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PresentationDto {
    @Schema(description = "발표자료 idx")
    private Long presentationIdx;
    @Schema(description = "발표 제목")
    private String title;
    @Schema(description = "발표자 닉네임")
    private String nickname;
    @Schema(description = "발표자 이미지 url")
    private String profileImgUrl;
    @Schema(description = "발표자 소속")
    private String organization;
    @Schema(description = "발표 내용 요약")
    private String content;
    @Schema(description = "발표자료 url")
    private String presentationUrl;
}
