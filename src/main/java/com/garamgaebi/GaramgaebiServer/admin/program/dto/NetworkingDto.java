package com.garamgaebi.GaramgaebiServer.admin.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Data
public class NetworkingDto {
    @NotBlank(message = "네트워킹명을 입력해주세요.")
    private String title;
    @NotNull
    @Future
    private LocalDateTime date;
    @NotBlank(message = "네트워킹 장소를 입력해주세요.")
    private String location;
    private Integer fee;

}
