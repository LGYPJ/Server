package com.garamgaebi.GaramgaebiServer.domain.notification.event;

import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProgramOpenEvent {

    private Program program;
}
