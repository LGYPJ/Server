package com.garamgaebi.GaramgaebiServer.global.util.scheduler.event;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import lombok.Getter;

@Getter
public class PostProgramEvent {
    private Program program;

    public PostProgramEvent(Program program) {
        this.program =program;
    }
}
