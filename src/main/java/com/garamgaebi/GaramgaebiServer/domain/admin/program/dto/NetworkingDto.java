package com.garamgaebi.GaramgaebiServer.domain.admin.program.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
