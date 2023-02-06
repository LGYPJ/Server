package com.garamgaebi.GaramgaebiServer.global.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DeadlineProgramJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 알림 보내기
        System.out.println("마감 임박 알림");
    }
}
