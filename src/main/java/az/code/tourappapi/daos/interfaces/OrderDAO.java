package az.code.tourappapi.daos.interfaces;

import az.code.tourappapi.models.Order;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface OrderDAO {
    Order save(@NotNull Order order);

    void delete(@NotNull Long id);

    Optional<Order> find(@NotNull Long id);

    boolean exists(@NotNull Long id);
}
