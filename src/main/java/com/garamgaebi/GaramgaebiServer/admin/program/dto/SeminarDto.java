package com.garamgaebi.GaramgaebiServer.admin.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Data
public class SeminarDto {

    @NotBlank(message = "세미나명을 입력해주세요.")
    private String title;
    @NotNull
    @Future
    private LocalDateTime date;
    @NotBlank(message = "세미나 장소를 입력해주세요.")
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
