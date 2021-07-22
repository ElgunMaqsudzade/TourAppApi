package az.code.tourappapi.services;

import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.OrderDTO;
import az.code.tourappapi.services.interfaces.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final ObjectMapper mapper;

    @Override
    public OrderDTO update(@NotNull Long id, @NotNull Order order) {
        return mapper.convertValue(orderDAO.save(order.toBuilder().id(id).build()),OrderDTO.class);
    }

    @Override
    public OrderDTO create(@NotNull Order order) {
        return mapper.convertValue(orderDAO.save(order),OrderDTO.class);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        orderDAO.deleteById(20L);
    }

    @Override
    public Order findById(@NotNull Long id) {
        Optional<Order> order = orderDAO.findById(id);
        if (order.isEmpty()) throw new DataNotFound("Order not found in database");
        return order.get();
    }

    @Override
    public boolean existsById(@NotNull Long id) {
        return orderDAO.existsById(id);
    }
}
