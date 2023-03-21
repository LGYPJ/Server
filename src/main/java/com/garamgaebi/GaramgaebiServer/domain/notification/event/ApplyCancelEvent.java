package com.garamgaebi.GaramgaebiServer.domain.notification.event;

import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyCancelEvent {

    private Apply apply;
}
