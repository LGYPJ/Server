package com.garamgaebi.GaramgaebiServer.domain.notification.event;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import lombok.Getter;

@Getter
public class DeadlineEvent {
    private Program program;

    public DeadlineEvent(Program program) {
        this.program =program;
    }
}
