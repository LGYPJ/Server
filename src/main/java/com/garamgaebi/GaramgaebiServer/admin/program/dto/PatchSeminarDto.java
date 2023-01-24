package com.garamgaebi.GaramgaebiServer.admin.program.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PatchSeminarDto {

    private Long idx;
    private String title;
    private LocalDateTime date;
    private String location;
    private Integer fee;

}
