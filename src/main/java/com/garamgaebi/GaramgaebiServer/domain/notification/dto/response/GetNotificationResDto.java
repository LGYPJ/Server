package com.garamgaebi.GaramgaebiServer.domain.notification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "알림 목록 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class GetNotificationResDto {
    @Schema(description = "알림 목록")
    private List<GetNotificationDto> result;
    @Schema(description = "다음 페이지 존재 여부")
    private boolean hasNext;
}
