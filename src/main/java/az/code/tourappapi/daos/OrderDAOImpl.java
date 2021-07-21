package az.code.tourappapi.daos;

import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.repos.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {
    private final OrderRepo orderRepo;

    @Override
    public Order save(@NotNull Order order) {
        return orderRepo.save(order);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        orderRepo.deleteById(id);
    }

    @Override
    public Optional<Order> findById(@NotNull Long id) {
        return orderRepo.findById(id);
    }

    @Override
    public boolean existsById(@NotNull Long id) {
        return orderRepo.existsById(id);
    }
}
