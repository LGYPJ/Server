package com.garamgaebi.GaramgaebiServer.global.scheduler.listener;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.global.scheduler.QuartzSchedulerService;
import com.garamgaebi.GaramgaebiServer.global.scheduler.event.DeleteProgramEvent;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DeleteScheduleListener {

    private final QuartzSchedulerService quartzSchedulerService;

    @TransactionalEventListener
    @Async
    public void deleteProgramSchedule(DeleteProgramEvent deleteProgramEvent) {
        try {
            quartzSchedulerService.deleteProgramJob(deleteProgramEvent.getProgram());
        } catch(SchedulerException e) {
            // 삭제 실패 로그 찍기
        }
    }
}
