package com.garamgaebi.GaramgaebiServer.domain.program.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Schema(description = "프로그램 상세정보 요청 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ProgramDetailReq {
    @Schema(description = "멤버 idx")
    @NotNull @PositiveOrZero
    private Long memberIdx;
    @Schema(description = "프로그램 idx")
    @NotNull @PositiveOrZero
    private Long programIdx;
}
