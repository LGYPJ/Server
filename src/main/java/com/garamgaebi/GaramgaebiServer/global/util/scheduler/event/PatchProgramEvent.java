package com.garamgaebi.GaramgaebiServer.global.util.scheduler.event;

import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import lombok.Getter;

@Getter
public class PatchProgramEvent {
    private Program program;

    public PatchProgramEvent(Program program) {
        this.program =program;
    }
}
