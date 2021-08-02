package az.code.tourappapi.components;

import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.jobs.EmailVerificationJob;
import az.code.tourappapi.jobs.ExpireOrderJob;
import az.code.tourappapi.jobs.SendToAgentJob;
import az.code.tourappapi.jobs.SendToSubscriberJob;
import az.code.tourappapi.models.Offer;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.dtos.TimerInfoDTO;
import az.code.tourappapi.utils.TimerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerExecutor {
    private final Scheduler scheduler;
    private final AppConfig conf;
    private final TimerUtil util;


    public void runEmailVerification(Token token) {
        scheduler.schedule(EmailVerificationJob.class, TimerInfoDTO.builder().fireCount(1).data(token).build());
    }

    public void runSendToSubscriberJob(Offer offer) {
        scheduler.schedule(SendToSubscriberJob.class, TimerInfoDTO.builder().fireCount(1).data(offer).build());
    }

    public void runSendToAgentJob(Order order) {
        scheduler.schedule(SendToAgentJob.class, TimerInfoDTO.builder().fireCount(1).data(order).build());
    }

    public void runExpireOrderJob(Order order) {
        scheduler.schedule(ExpireOrderJob.class, TimerInfoDTO.builder()
                .offsetMS(util.offsetMS())
                .fireCount(1)
                .data(order)
                .build());
    }
}
