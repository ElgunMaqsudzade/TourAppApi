package az.code.tourappapi.services;

import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;

    @Override
    public Order update(@NotNull Long id, @NotNull Order order) {
        return orderDAO.save(order);
    }

    @Override
    public Order create(@NotNull Order order) {
        return orderDAO.save(order);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        orderDAO.deleteById(id);
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
