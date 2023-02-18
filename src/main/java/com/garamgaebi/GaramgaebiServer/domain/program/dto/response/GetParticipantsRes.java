package com.garamgaebi.GaramgaebiServer.domain.program.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "프로그램 신청자 정보 응답 Wrapper DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class GetParticipantsRes {
    @Schema(description = "신청자 리스트")
    private List<ParticipantDto> participantList = new ArrayList<>();
    @Schema(description = "멤버 포함 여부")
    private Boolean isApply;
}
