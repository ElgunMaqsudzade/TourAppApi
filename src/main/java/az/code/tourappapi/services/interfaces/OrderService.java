package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.dtos.OrderDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;
import az.code.tourappapi.models.enums.OrderStatus;

import javax.validation.constraints.NotNull;

public interface OrderService {
    void create(@NotNull OrderDTO order);

    void archive(@NotNull AppUser user, @NotNull Long orderId, @NotNull Boolean archive);

    PaginationDTO<OrderDTO> findAll(@NotNull AppUser user, Integer page, Integer count);

    PaginationDTO<OrderDTO> findAllOffered(@NotNull AppUser user, Integer page, Integer size);

    PaginationDTO<OrderDTO> findAllArchived(@NotNull AppUser user, Integer page, Integer count);
}
