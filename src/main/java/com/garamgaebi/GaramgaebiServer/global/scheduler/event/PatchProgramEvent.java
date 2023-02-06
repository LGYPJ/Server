package com.garamgaebi.GaramgaebiServer.global.scheduler.event;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import lombok.Getter;

@Getter
public class PatchProgramEvent {
    private Program program;

    public PatchProgramEvent(Program program) {
        this.program =program;
    }
}
