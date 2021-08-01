package az.code.tourappapi.utils;

import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.configs.BasicConfig;
import az.code.tourappapi.models.dtos.TimerInfoDTO;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TimerUtil {
    private final AppConfig conf;

    public static JobDetail buildJobDetail(Class<? extends Job> jobClass, TimerInfoDTO infoDTO) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), infoDTO);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName() + UUID.randomUUID())
                .setJobData(jobDataMap)
                .build();
    }

    public static Trigger buildTrigger(Class<? extends Job> jobClass, TimerInfoDTO infoDTO) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInMilliseconds(infoDTO.getIntervalMS());

        ScheduleBuilder schedule;

        if (infoDTO.getCron() != null) {
            schedule = CronScheduleBuilder.cronSchedule(infoDTO.getCron());
        } else {
            if (infoDTO.isForever()) {
                schedule = builder.repeatForever();
            } else {
                schedule = builder.withRepeatCount(infoDTO.getFireCount() - 1);
            }
        }


        return TriggerBuilder.newTrigger()
                .withIdentity(jobClass.getSimpleName() + UUID.randomUUID())
                .withSchedule(schedule)
                .startAt(new Date(System.currentTimeMillis() + infoDTO.getOffsetMS()))
                .build();
    }


    public boolean isAppropriate() {
        LocalTime start = conf.getWorkHours().getStart();
        LocalTime end = conf.getWorkHours().getEnd();
        return LocalTime.now().isAfter(start) && LocalTime.now().isBefore(end);
    }

    public Long offsetMS() {
        return conf.getDurationHour() * 60 * 60 * 1000;
    }
}
