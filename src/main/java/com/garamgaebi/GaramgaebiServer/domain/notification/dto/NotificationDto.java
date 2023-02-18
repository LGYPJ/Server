package com.garamgaebi.GaramgaebiServer.domain.notification.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.status.notification.NotificationType;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "푸쉬 알림 전송 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class NotificationDto {
    @Schema(description = "알림 종류")
    private NotificationType notificationType;
    @Schema(description = "알림 내용")
    private String content;
    @Schema(description = "관련 프로그램 idx")
    private Long resourceIdx;
    @Schema(description = "관련 프로그램 타입")
    private ProgramType resourceType;
}
