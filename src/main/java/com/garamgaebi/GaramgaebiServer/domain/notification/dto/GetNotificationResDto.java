package com.garamgaebi.GaramgaebiServer.domain.notification.dto;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class GetNotificationResDto {
    private List<GetNotificationDto> result;
    private boolean hasNext;
}
