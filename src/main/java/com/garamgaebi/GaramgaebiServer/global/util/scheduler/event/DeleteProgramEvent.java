package com.garamgaebi.GaramgaebiServer.global.util.scheduler.event;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import lombok.Getter;

@Getter
public class DeleteProgramEvent {
    private Program program;

    public DeleteProgramEvent(Program program) {
        this.program =program;
    }
}
