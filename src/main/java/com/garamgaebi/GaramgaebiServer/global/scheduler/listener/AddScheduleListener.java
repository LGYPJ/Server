package com.garamgaebi.GaramgaebiServer.global.scheduler.listener;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.global.scheduler.QuartzSchedulerService;
import com.garamgaebi.GaramgaebiServer.global.scheduler.event.PatchProgramEvent;
import com.garamgaebi.GaramgaebiServer.global.scheduler.event.PostProgramEvent;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AddScheduleListener {

    private final QuartzSchedulerService quartzSchedulerService;

    @TransactionalEventListener
    @Async
    public void addProgramSchedule(PostProgramEvent postProgramEvent) {
        try {
            quartzSchedulerService.addProgramJob(postProgramEvent.getProgram());
            System.out.println("프로그램 등록 이벤트");
        } catch(SchedulerException e) {
            // 실패 로그 찍기
        }
    }

    @TransactionalEventListener
    @Async
    public void addProgramSchedule(PatchProgramEvent patchProgramEvent) {
        try {
            quartzSchedulerService.addProgramJob(patchProgramEvent.getProgram());
            System.out.println("프로그램 수정 이벤트");
        } catch(SchedulerException e) {
            // 실패 로그 찍기
        }
    }
}
