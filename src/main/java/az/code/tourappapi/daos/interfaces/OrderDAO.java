package az.code.tourappapi.daos.interfaces;

import az.code.tourappapi.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface OrderDAO {
    Order save(@NotNull Order order);

    void delete(@NotNull Long id);

    Order find(@NotNull Long id);

    Optional<Order> find(@NotNull Specification<Order> spec);

    void archive(@NotNull Long userId, @NotNull Long orderId, @NotNull Boolean archive);

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    Page<Order> findAll(Pageable pageable);

    boolean exists(@NotNull Long id);
}
