package com.garamgaebi.GaramgaebiServer.domain.notification.event;

import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyCancelEvent {

    private Apply apply;
}
