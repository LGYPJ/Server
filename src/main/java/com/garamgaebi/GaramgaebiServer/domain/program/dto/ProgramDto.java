package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProgramDto {
    private Long programIdx;
    private String title;
    private LocalDateTime date;
    private String location;
}
