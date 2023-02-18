package com.garamgaebi.GaramgaebiServer.domain.program.dto.response;

import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramUserButtonStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "프로그램 상세정보 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
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
    @Schema(description = "유저 버튼 상태(마감/신청완료/신청확인중/신청하기)", allowableValues = {"CLOSED", "APPLY_COMPLETE", "BEFORE_APPLY_CONFIRM", "APPLY"})
    private ProgramUserButtonStatus userButtonStatus;

}
