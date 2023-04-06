package com.garamgaebi.GaramgaebiServer.domain.admin.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType;
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

}
