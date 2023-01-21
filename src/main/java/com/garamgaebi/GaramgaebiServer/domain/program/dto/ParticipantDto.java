package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ParticipantDto {
    private Long memberIdx;
    private String nickname;
    private String profileImg;
}
