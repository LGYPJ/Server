package com.garamgaebi.GaramgaebiServer.global.scheduler;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.DeadlineEvent;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.service.ProgramService;
import com.garamgaebi.GaramgaebiServer.global.scheduler.job.CloseProgramJob;
import com.garamgaebi.GaramgaebiServer.global.scheduler.job.DeadlineProgramJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
@RequiredArgsConstructor
public class QuartzSchedulerService {

    private final ApplicationContext applicationContext;

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    // 어플리케이션 실행시 오픈된 프로그램들 검사
    @PostConstruct
    @Transactional
    public void start() throws SchedulerException {
        // Open인 프로그램들 등록
        List<Program> programs = applicationContext.getBean(ProgramRepository.class).findByStatus(ProgramStatus.OPEN);

        for(Program program : programs) {
            // 스케줄러에 등록
            addProgramJob(program);

        }
    }

    // 스케줄러에 이미 있는 job이면 수정, 없는 job이면 추가
    public void addProgramJob(Program program) throws SchedulerException {
        JobKey closeJobKey = null;
        JobKey deadlineJobKey = null;
        JobDetail closeJobDetail;
        JobDetail deadlineJobDetail;
        Trigger closeTrigger;
        Trigger deadlineTrigger;

        schedulerFactory.getScheduler().start();


        // 마감 기간 지난 경우 -> 바로 close
        if(program.getEndDate().isBefore(LocalDateTime.now())) {
            // 기존에 등록된 스케줄이 있었다면 삭제해주고
            deleteProgramJob(program);

            applicationContext.getBean(ProgramService.class).closeProgram(program.getIdx());
            return;
        }
        System.out.println(program.getDeadlineAlertTime());
        // 알림 시간 지난 경우 -> close 스케줄만 등록
        if(program.getDeadlineAlertTime().isBefore(LocalDateTime.now())) {

            // 기존에 등록된 스케줄이 있었다면 삭제해주고
            deleteProgramJob(program);

            closeTrigger = buildJobTrigger(program.getEndDate(), getCloseTriggerKey(program));
            closeJobDetail = buildJobDetail(CloseProgramJob.class, program, applicationContext);
            closeJobKey = getCloseJobKey(program);

            schedulerFactory.getScheduler().scheduleJob(closeJobDetail, closeTrigger);
            // 성공 로그
            return;
        }

        closeTrigger = buildJobTrigger(program.getEndDate(), getCloseTriggerKey(program));
        deadlineTrigger = buildJobTrigger(program.getDeadlineAlertTime(), getDeadlineTriggerKey(program));

        closeJobDetail = buildJobDetail(CloseProgramJob.class, program, applicationContext);
        deadlineJobDetail = buildJobDetail(DeadlineProgramJob.class, program, applicationContext);

        closeJobKey = getCloseJobKey(program);
        deadlineJobKey = getDeadlineJobKey(program);
        if(isJobExist(closeJobKey)) {
            schedulerFactory.getScheduler().rescheduleJob(closeTrigger.getKey(), closeTrigger);
            schedulerFactory.getScheduler().rescheduleJob(deadlineTrigger.getKey(), deadlineTrigger);
            // 업데이트 했다는 로그
            return;
        }

        schedulerFactory.getScheduler().scheduleJob(closeJobDetail, closeTrigger);
        schedulerFactory.getScheduler().scheduleJob(deadlineJobDetail, deadlineTrigger);
        // 성공했다는 로그
    }

    // 등록된 job 삭제
    public void deleteProgramJob(Program program) throws SchedulerException{
        JobKey closeJobKey = getCloseJobKey(program);
        JobKey deadlineJobKey = getDeadlineJobKey(program);

        if(isJobExist(closeJobKey)) {
            schedulerFactory.getScheduler().deleteJob(closeJobKey);
        }
        if(isJobExist(deadlineJobKey)) {
            schedulerFactory.getScheduler().deleteJob(deadlineJobKey);
        }
        // 삭제했다는 로그
    }

    // job 존재 여부 확인
    private boolean isJobExist(JobKey jobKey) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        if (scheduler.checkExists(jobKey)) {
            return true;
        }
        return false;
    }

    // job detail 생성 -> 실행될 클래스와 파라미터 등
    private JobDetail buildJobDetail(Class job, Program program, ApplicationContext applicationContext) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(job);
        factoryBean.setDurability(false);
        if(job.isInstance(CloseProgramJob.class)) {
            factoryBean.setName(program.getTitle() + "Close");
        }
        else if(job.isInstance(DeadlineProgramJob.class)) {
            factoryBean.setName(program.getTitle() + "Deadline");
        }

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("programIdx", program.getIdx());
        jobDataMap.put("applicationContext", applicationContext);
        factoryBean.setJobDataAsMap(jobDataMap);

        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    // 트리거 생성 -> 잡의 실행 시점 정의
    private Trigger buildJobTrigger(LocalDateTime firedTime, TriggerKey triggerKey) {
        return TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(transTimeToCron(firedTime))).build();
    }

    // LocalDateTime을 cron 표현식으로 변경
    private String transTimeToCron(LocalDateTime target) {
        String transTarget = target.format(DateTimeFormatter.ofPattern("ss mm HH dd MM ? yyyy"));
        return transTarget;
    }

    // jobKey, triggerKey 생성기
    public JobKey getCloseJobKey(Program program) {return JobKey.jobKey("CloseProgramJob"+program.getIdx());}
    public JobKey getDeadlineJobKey(Program program) {return JobKey.jobKey("DeadlineProgramJob"+program.getIdx());}
    public TriggerKey getCloseTriggerKey(Program program) {return TriggerKey.triggerKey("CloseProgramTrigger"+program.getIdx());}
    public TriggerKey getDeadlineTriggerKey(Program program) {return TriggerKey.triggerKey("CloseDeadlineTrigger"+program.getIdx());}
}
