package com.garamgaebi.GaramgaebiServer.domain.admin.program.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Data
public class PatchNetworkingDto {

    private Long idx;
    @NotBlank(message = "수정할 네트워크 명을 입력해주세요.")
    private String title;
    @Future
    private LocalDateTime date;
    @NotBlank(message = "네트워킹 장소를 입력해주세요.")
    private String location;
    private Integer fee;
}
