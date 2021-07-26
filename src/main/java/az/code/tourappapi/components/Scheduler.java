package az.code.tourappapi.components;



import az.code.tourappapi.models.dtos.TimerInfoDTO;
import az.code.tourappapi.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Slf4j
public class Scheduler{

    private final org.quartz.Scheduler scheduler;

    public Scheduler(org.quartz.Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    public <T> void schedule(Class<? extends Job> jobClass, TimerInfoDTO<T> infoDTO) {
        JobDetail jobDetail = TimerUtil.buildJobDetail(jobClass, infoDTO);
        Trigger trigger = TimerUtil.buildTrigger(jobClass, infoDTO);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    public void deleteTimer(final String timerId) {
        try {
            scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }


    @PreDestroy
    public void preDestroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }
}
