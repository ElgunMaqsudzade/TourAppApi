package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.OrderDTO;

import javax.validation.constraints.NotNull;

public interface OrderService {
    OrderDTO update(@NotNull Long id, @NotNull Order order);

    OrderDTO create(@NotNull Order order);

    void delete(@NotNull Long id);

    Order find(@NotNull Long id);

    boolean exists(@NotNull Long id);
}
