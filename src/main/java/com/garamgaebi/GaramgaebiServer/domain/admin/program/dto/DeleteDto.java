package com.garamgaebi.GaramgaebiServer.domain.admin.program.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Data
public class DeleteDto {
    private String title;
}
