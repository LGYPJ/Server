package com.garamgaebi.GaramgaebiServer.domain.notification.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.NotificationType;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "알림 조회 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class GetNotificationDto {
    @Schema(description = "알림 idx")
    private Long notificationIdx;
    @Schema(description = "알림 종류")
    private NotificationType notificationType;
    @Schema(description = "알림 내용")
    private String content;
    @Schema(description = "관련 프로그램 idx")
    private Long resourceIdx;
    @Schema(description = "관련 프로그램 타입")
    private ProgramType resourceType;
    @Schema(description = "알림 읽음 여부")
    private Boolean isRead;
}
