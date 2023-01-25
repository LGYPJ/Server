package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Schema(description = "프로그램 정보 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
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
    @Schema(description = "프로그램 유료(PREMIUM)/무료(FREE) 여부")
    private String payment;
    @Schema(description = "프로그램 상태(이번달(THIS_MONTH)/예정(READY)/마감(CLOSED))")
    private String status;
}
