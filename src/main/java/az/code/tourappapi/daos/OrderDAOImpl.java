package az.code.tourappapi.daos;

import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.repos.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {
    private final OrderRepo orderRepo;

    @Override
    public Order save(@NotNull Order order) {
        return orderRepo.save(order);
    }

    @Override
    public void delete(@NotNull Long id) {
        orderRepo.deleteById(id);
    }

    @Override
    public Order find(Long orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isEmpty()) throw new DataNotFound("Order not found in database");

        return order.get();
    }

    @Override
    public Optional<Order> find(@NotNull Specification<Order> spec) {
        return orderRepo.findOne(spec);
    }

    @Override
    public void archive(Long userId, Long orderId, Boolean archive) {
        orderRepo.archive(userId, orderId, archive);
    }

    @Override
    public Page<Order> findAll(@NotNull Specification<Order> spec, Pageable pageable) {
        return orderRepo.findAll(spec, pageable);
    }

    @Override
    public Page<Order> findAll(@NotNull Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    @Override
    public boolean exists(@NotNull Long id) {
        return orderRepo.existsById(id);
    }
}
