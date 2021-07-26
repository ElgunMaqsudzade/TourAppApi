package az.code.tourappapi.components;

import az.code.tourappapi.jobs.EmailVerificationJob;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.dtos.TimerInfoDTO;
import az.code.tourappapi.utils.TimerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerExecutor {
    private final Scheduler scheduler;



    public void runEmailVerification(Token token) {
        scheduler.schedule(EmailVerificationJob.class, TimerInfoDTO.builder().fireCount(1).data(token).build());
    }
}
