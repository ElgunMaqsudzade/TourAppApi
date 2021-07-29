package az.code.tourappapi.jobs;

import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.models.AppUserOrder;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.TimerInfoDTO;
import az.code.tourappapi.models.enums.OrderStatus;
import az.code.tourappapi.repos.AppUserRepo;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ExpireOrderJob implements Job {
    private final OrderDAO orderDAO;


    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        TimerInfoDTO<Order> infoDTO = (TimerInfoDTO<Order>) ctx.getJobDetail().getJobDataMap().get(ExpireOrderJob.class.getSimpleName());
        Order order = infoDTO.getData();

        Order newOrder = orderDAO.find(order.getId());

        Set<AppUserOrder> userOrders = newOrder.getAppUserOrders()
                .parallelStream()
                .peek(i -> i.setStatus(OrderStatus.EXPIRED))
                .collect(Collectors.toSet());

        orderDAO.save(newOrder.toBuilder().appUserOrders(userOrders).build());
    }
}