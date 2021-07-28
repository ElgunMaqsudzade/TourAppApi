package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.OrderDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;

import javax.validation.constraints.NotNull;

public interface OrderService {
    void create(@NotNull OrderDTO order);


    OrderDTO update(@NotNull Long orderId, @NotNull OrderDTO order);

    OrderDTO find(@NotNull AppUser user, @NotNull Long id);

    PaginationDTO<OrderDTO> findAll(@NotNull AppUser user, Integer page, Integer count);
}
