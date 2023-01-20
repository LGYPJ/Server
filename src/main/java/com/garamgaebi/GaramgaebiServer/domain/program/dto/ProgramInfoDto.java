package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ProgramInfoDto {
    private Long seminarIdx;
    private String title;
    private LocalDateTime date;
    private String location;
    private Integer fee;
    private LocalDateTime endDate;
    // 세미나 상태 : 오픈 예정, 오픈, (마감, 마감 확인) = 같은 마감
    private ProgramStatus seminarStatus;
    // 유저 상태 : 신청 가능, 불가능
    private String userStatus;

}
