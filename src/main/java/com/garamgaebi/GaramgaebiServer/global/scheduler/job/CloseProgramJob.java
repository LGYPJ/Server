package com.garamgaebi.GaramgaebiServer.global.scheduler.job;

import com.garamgaebi.GaramgaebiServer.domain.program.service.ProgramService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CloseProgramJob extends QuartzJobBean {

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Long programIdx = context.getJobDetail().getJobDataMap().getLong("programIdx");
        ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");

        applicationContext.getBean(ProgramService.class).closeProgram(programIdx);
    }
}
