package com.garamgaebi.GaramgaebiServer.global.util.scheduler.job;

import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.DeadlineEvent;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Optional;

public class DeadlineProgramJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Long programIdx = context.getJobDetail().getJobDataMap().getLong("programIdx");
        ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");

        Optional<Program> programWrapper = applicationContext.getBean(ProgramRepository.class).findById(programIdx);

        if(programWrapper.isEmpty()) {
            // 없는 프로그램 실패 로그
            return;
        }

        // 알림 보내기
        applicationContext.publishEvent(new DeadlineEvent(programWrapper.get()));
    }
}
