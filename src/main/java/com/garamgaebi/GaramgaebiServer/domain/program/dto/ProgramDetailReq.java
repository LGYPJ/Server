package com.garamgaebi.GaramgaebiServer.domain.program.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "프로그램 상세정보 요청 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProgramDetailReq {
    @Schema(description = "멤버 idx")
    @NotNull @PositiveOrZero
    private Long memberIdx;
    @Schema(description = "프로그램 idx")
    @NotNull @PositiveOrZero
    private Long programIdx;
}
