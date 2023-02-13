package com.garamgaebi.GaramgaebiServer.domain.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "프로그램 신청자 정보 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ParticipantDto {
    @Schema(description = "멤버 idx")
    private Long memberIdx;
    @Schema(description = "멤버 닉네임")
    private String nickname;
    @Schema(description = "멤버 프로필 이미지 url")
    private String profileImg;
}
