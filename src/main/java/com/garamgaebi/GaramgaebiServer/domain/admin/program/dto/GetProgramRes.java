package com.garamgaebi.GaramgaebiServer.domain.admin.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class GetProgramRes {
    private Long programIdx;
    private String title;
    private LocalDateTime date;
    private String location;
    private Integer fee;
    private LocalDateTime closeDate;
    private ProgramStatus status;

    @Builder
    public GetProgramRes(
            Long programIdx,
            String title,
            LocalDateTime date,
            String location,
            Integer fee,
            LocalDateTime closeDate,
            ProgramStatus status
    ) {
        this.programIdx = programIdx;
        this.title = title;
        this.date = date;
        this.location = location;
        this.fee = fee;
        this.closeDate = closeDate;
        this.status = status;
    }

}
