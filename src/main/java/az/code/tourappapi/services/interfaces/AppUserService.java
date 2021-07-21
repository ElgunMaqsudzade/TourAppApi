package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.AppUser;

import javax.validation.constraints.NotNull;


public interface AppUserService {

    AppUser update(@NotNull Long id, @NotNull AppUser appUser);

    AppUser create(@NotNull AppUser appUser);

    void deleteById(@NotNull Long id);

    AppUser findById(@NotNull Long id);

    boolean existsById(@NotNull Long id);
}
