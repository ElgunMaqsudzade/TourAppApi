package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.dtos.AppUserDTO;

import javax.validation.constraints.NotNull;


public interface AppUserService {

    AppUserDTO update(@NotNull Long id, @NotNull AppUserDTO appUser);

    AppUserDTO create(@NotNull AppUserDTO appUser);

    void delete(@NotNull Long id);

    AppUserDTO find(@NotNull Long id);

    AppUser find(@NotNull String email);

    boolean exists(@NotNull Long id);

    boolean isNew(@NotNull AppUserDTO appUserDTO);
}
