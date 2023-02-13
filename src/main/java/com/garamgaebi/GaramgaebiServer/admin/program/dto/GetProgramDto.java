package com.garamgaebi.GaramgaebiServer.admin.program.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class GetProgramDto {
    private Long idx;
    private String title;
    private LocalDateTime date;
    private String location;
    private Integer fee;
    private LocalDateTime closeDate;
}
