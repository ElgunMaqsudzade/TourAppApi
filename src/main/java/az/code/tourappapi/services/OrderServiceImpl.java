package az.code.tourappapi.services;

import az.code.tourappapi.components.SchedulerExecutor;
import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.AppUserOrder;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.OrderDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;
import az.code.tourappapi.models.enums.OrderStatus;
import az.code.tourappapi.services.interfaces.OrderService;
import az.code.tourappapi.utils.ModelMapperUtil;
import az.code.tourappapi.components.specs.interfaces.OrderSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final OrderSpec orderSpec;
    private final ModelMapperUtil util;
    private final AppConfig conf;
    private final SchedulerExecutor sch;

    @Override
    public void create(@NotNull OrderDTO orderDTO) {
        Order order = util.map(orderDTO, Order.class).toBuilder().createDate(LocalDateTime.now()).build();
        orderDAO.save(order);
        sch.runExpireOrderJob(order);
        sch.runSendToAgentJob(order);
    }

    @Override
    public void archive(@NotNull AppUser user, @NotNull Long orderId, @NotNull Boolean archive) {
        orderDAO.archive(user.getId(), orderId, archive);
    }


    @Override
    public PaginationDTO<OrderDTO> findAll(@NotNull AppUser user, Integer page, Integer size) {
        return findAll(user, page, size, false, false);
    }

    @Override
    public PaginationDTO<OrderDTO> findAllOffered(@NotNull AppUser user, Integer page, Integer size) {
        return findAll(user, page, size, false, true);
    }

    @Override
    public PaginationDTO<OrderDTO> findAllArchived(@NotNull AppUser user, Integer page, Integer size) {
        return findAll(user, page, size, true, false);
    }

    private PaginationDTO<OrderDTO> findAll(@NotNull AppUser user,
                                            Integer page,
                                            Integer size,
                                            @NotNull Boolean archived,
                                            @NotNull Boolean onlyOffered) {
        Specification<Order> spec = Specification
                .where(orderSpec.exclude(OrderStatus.EXPIRED))
                .and(orderSpec.archive(archived))
                .and(orderSpec.forUser(user))
                .and(orderSpec.onlyOffered(onlyOffered))
                .and(orderSpec.afterThan(user.getCreateDate()));

        Page<Order> p = orderDAO.findAll(spec, PageRequest.of(page, size));

        PaginationDTO<OrderDTO> pOrder = util.toPagination(p, OrderDTO.class);

        p.getContent().parallelStream().forEach(o -> pOrder.getItems()
                .parallelStream()
                .filter(i -> i.getId().equals(o.getId()))
                .findFirst()
                .ifPresent(i -> {
                    i.setExpireTime(o.getCreateDate().plusHours(conf.getDurationHour()));
                    o.getAppUserOrders()
                            .parallelStream()
                            .filter(e -> e.getAppUser().getId().equals(user.getId()))
                            .map(AppUserOrder::getStatus)
                            .max(Comparator.comparing(OrderStatus::ordinal))
                            .ifPresent(i::setOrderStatus);
                }));
        return pOrder;
    }
}
