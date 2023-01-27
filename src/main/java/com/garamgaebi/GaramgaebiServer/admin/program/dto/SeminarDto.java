package com.garamgaebi.GaramgaebiServer.admin.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SeminarDto {

    private String title;
    private LocalDateTime date;
    private String location;
    private Integer fee;
    private List<PostPresentationDto> presentations;

    public Program toEntity() {
        Program program = new Program();

        program.setTitle(this.title);
        program.setDate(this.date);
        program.setLocation(this.location);
        program.setFee(this.fee);
        program.setStatus(ProgramStatus.READY_TO_OPEN);
        program.setProgramType(ProgramType.SEMINAR);

        return program;
    }

}
