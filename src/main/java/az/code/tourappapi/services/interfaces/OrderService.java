package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.Order;

import javax.validation.constraints.NotNull;

public interface OrderService {
    Order update(@NotNull Long id, @NotNull Order order);

    Order create(@NotNull Order order);

    void deleteById(@NotNull Long id);

    Order findById(@NotNull Long id);

    boolean existsById(@NotNull Long id);
}
