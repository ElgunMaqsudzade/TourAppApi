package az.code.tourappapi.jobs;

import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.TimerInfoDTO;
import az.code.tourappapi.models.enums.OrderStatus;
import az.code.tourappapi.repos.AppUserRepo;
import az.code.tourappapi.services.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendToAgentJob implements Job {
    private final AppUserRepo userRepo;
    private final AppUserService userService;


    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        TimerInfoDTO<Order> infoDTO = (TimerInfoDTO<Order>) ctx.getJobDetail().getJobDataMap().get(SendToAgentJob.class.getSimpleName());
        Order order = infoDTO.getData();
        userRepo.findAll().parallelStream().forEach(i -> userService.addOrder(i, order, OrderStatus.NEW));
    }
}
