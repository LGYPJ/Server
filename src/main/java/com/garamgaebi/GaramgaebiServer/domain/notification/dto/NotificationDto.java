package com.garamgaebi.GaramgaebiServer.domain.notification.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "알림 요청 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDto {
    @Schema(description = "알림 종류")
    private String notificationType;
    @Schema(description = "알림 내용")
    private String content;
    @Schema(description = "관련 프로그램 idx")
    private Long resourceIdx;
    @Schema(description = "관련 프로그램 타입")
    private ProgramType resourceType;
}
