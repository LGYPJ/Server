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
    @Schema(description = "프로그램 상태 (오픈/마감)")
    private ProgramStatus programStatus;
    @Schema(description = "유저 상태(신청 가능/신청 불가능)")
    private String userStatus;

}
