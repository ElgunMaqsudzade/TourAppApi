package az.code.tourappapi.daos.interfaces;

import az.code.tourappapi.models.Order;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface OrderDAO {
    Order save(@NotNull Order order);

    void deleteById(@NotNull Long id);

    Optional<Order> findById(@NotNull Long id);

    boolean existsById(@NotNull Long id);
}
