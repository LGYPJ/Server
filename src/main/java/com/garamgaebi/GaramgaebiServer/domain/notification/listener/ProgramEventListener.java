package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.notification.event.DeadlineEvent;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.ProgramOpenEvent;
import com.garamgaebi.GaramgaebiServer.global.util.scheduler.event.DeleteProgramEvent;
import org.springframework.context.event.EventListener;

public interface ProgramEventListener {

    // 프로그램 오픈 이벤트 핸들링
    @EventListener
    public void handleProgramOpenEvent(ProgramOpenEvent seminarOpenEvent);

    // 프로그램 마감 임박 이벤트 핸들링
    @EventListener
    public void handleProgramDeadlineEvent(DeadlineEvent deadlineEvent);

    @EventListener
    public void handleProgramDeleteEvent(DeleteProgramEvent deleteProgramEvent);
}
