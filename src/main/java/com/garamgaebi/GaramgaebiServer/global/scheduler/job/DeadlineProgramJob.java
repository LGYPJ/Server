package com.garamgaebi.GaramgaebiServer.global.scheduler.job;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.notification.dto.DeadlineEvent;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Optional;

public class DeadlineProgramJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Long programIdx = context.getJobDetail().getJobDataMap().getLong("programIdx");
        ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");

        ApplicationEventPublisher publisher = applicationContext.getBean(ApplicationEventPublisher.class);
        Optional<Program> programWrapper = applicationContext.getBean(ProgramRepository.class).findById(programIdx);

        if(programWrapper.isEmpty()) {
            // 없는 프로그램 실패 로그
            return;
        }

        publisher.publishEvent(new DeadlineEvent(programWrapper.get()));
        // 알림 보내기
        System.out.println("마감 임박 알림");
    }
}
