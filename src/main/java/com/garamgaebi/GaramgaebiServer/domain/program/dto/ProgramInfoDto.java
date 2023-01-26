package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "프로그램 상세정보 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ProgramInfoDto {
    @Schema(description = "프로그램 idx")
    private Long programIdx;
    @Schema(description = "프로그램 제목")
    private String title;
    @Schema(description = "프로그램 시행 일시")
    private LocalDateTime date;
    @Schema(description = "프로그램 시행 장소")
    private String location;
    @Schema(description = "프로그램 비용")
    private Integer fee;
    @Schema(description = "프로그램 신청 마감 일시")
    private LocalDateTime endDate;
    @Schema(description = "프로그램 상태 (오픈예정/오픈/마감)", allowableValues = {"READY_TO_OPEN", "OPEN", "CLOSED"})
    private ProgramStatus programStatus;
    @Schema(description = "유저 버튼 상태(마감/신청완료/신청확인 중/신청하기/신청취소/오픈예정/에러)", allowableValues = {"Closed", "ApplyComplete", "BeforeConfirmApply", "Apply", "ApplyCancel", "NotOpen", "Error"})
    private String userButtonStatus;

}
