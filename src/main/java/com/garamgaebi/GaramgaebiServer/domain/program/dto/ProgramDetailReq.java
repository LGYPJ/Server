package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProgramDetailReq {
    @NotNull @PositiveOrZero
    private Long memberIdx;
    @NotNull @PositiveOrZero
    private Long programIdx;
}
