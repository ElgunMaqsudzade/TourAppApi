package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Offer;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.enums.OrderStatus;

import javax.validation.constraints.NotNull;


public interface AppUserService {

    AppUserDTO update(@NotNull Long id, @NotNull AppUserDTO appUser);

    AppUserDTO create(@NotNull AppUserDTO appUser);

    void addOrder(@NotNull AppUser user, @NotNull Order order, @NotNull Offer offer, @NotNull OrderStatus status);

    void delete(@NotNull Long id);

    AppUserDTO find(@NotNull Long id);

    AppUser find(@NotNull String email);

    boolean exists(@NotNull Long id);

    boolean isNew(@NotNull AppUserDTO appUserDTO);
}
