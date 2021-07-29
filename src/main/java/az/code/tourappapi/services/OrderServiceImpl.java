package az.code.tourappapi.services;

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
import az.code.tourappapi.utils.specs.interfaces.SpecService;
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
    private final SpecService specService;
    private final ModelMapperUtil util;
    private final AppConfig conf;

    @Override
    public void create(@NotNull OrderDTO orderDTO) {
        Order order = util.map(orderDTO, Order.class);
        orderDAO.save(order.toBuilder().createDate(LocalDateTime.now()).build());
    }

    @Override
    public OrderDTO update(@NotNull Long id, @NotNull OrderDTO orderDTO) {
        Order order = util.map(orderDTO, Order.class);
        return util.map(orderDAO.save(order.toBuilder().id(id).build()), OrderDTO.class);
    }


    @Override
    public OrderDTO find(@NotNull AppUser user, @NotNull Long id) {
        return util.map(orderDAO.find(id), OrderDTO.class);
    }

    @Override
    public PaginationDTO<OrderDTO> findAll(@NotNull AppUser user, Integer page, Integer size) {
        return findAll(user, page, size, false);
    }

    @Override
    public PaginationDTO<OrderDTO> findAllArchived(@NotNull AppUser user, Integer page, Integer size) {
        return findAll(user, page, size, true);
    }

    private PaginationDTO<OrderDTO> findAll(@NotNull AppUser user, Integer page, Integer size, @NotNull Boolean archived) {
        Specification<Order> spec = Specification
                .where(specService.exclude(OrderStatus.EXPIRED))
                .and(specService.archived(archived, user))
                .and(specService.afterThan(user.getCreateDate()));

        Page<Order> p = orderDAO.findAll(spec, PageRequest.of(page, size));
        PaginationDTO<OrderDTO> pOrder = util.toPagination(p, OrderDTO.class);

        p.getContent().parallelStream()
                .forEach(o -> pOrder.getItems()
                        .parallelStream()
                        .filter(i -> i.getId().equals(o.getId()))
                        .findFirst()
                        .ifPresent(i -> {
                            i.setExpireTime(o.getCreateDate().plusHours(conf.getDurationHour()));
                            if (o.getAppUserOrders().isEmpty())
                                i.setOrderStatus(OrderStatus.NEW);
                            else
                                o.getAppUserOrders()
                                        .parallelStream()
                                        .map(AppUserOrder::getStatus)
                                        .max(Comparator.comparing(OrderStatus::ordinal))
                                        .ifPresent(i::setOrderStatus);
                        }));
        return pOrder;
    }
}
