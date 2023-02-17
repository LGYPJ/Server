package com.garamgaebi.GaramgaebiServer.domain.program.dto.response;

import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramOpenStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramPayStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramThisMonthStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


import java.time.LocalDateTime;

@Schema(description = "프로그램 정보 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ProgramDto {
    @Schema(description = "프로그램 idx")
    private Long programIdx;
    @Schema(description = "프로그램 제목")
    private String title;
    @Schema(description = "프로그램 일자")
    private LocalDateTime date;
    @Schema(description = "프로그램 시행 장소")
    private String location;
    @Schema(description = "프로그램 종류")
    private ProgramType type;
    @Schema(description = "프로그램 유료/무료 여부(음수일 경우 ERROR)", allowableValues = {"PREMIUM", "FREE", "ERROR"})
    private ProgramPayStatus payment;
    @Schema(description = "프로그램 상태(이번달/예정/마감)", allowableValues = {"THIS_MONTH", "READY", "CLOSED"})
    private ProgramThisMonthStatus status;
    @Schema(description = "프로그램 오픈 여부", allowableValues = {"OPEN", "BEFORE_OPEN"})
    private ProgramOpenStatus isOpen;
}
