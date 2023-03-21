package com.garamgaebi.GaramgaebiServer.domain.notification.event;

import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class RefundEvent {
    private List<Apply> applies = new ArrayList<Apply>();
    private Program program;
}
