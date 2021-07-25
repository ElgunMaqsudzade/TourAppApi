package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.dtos.SignInDTO;
import org.keycloak.representations.AccessTokenResponse;

import javax.validation.constraints.NotNull;


public interface AppUserService {

    AppUserDTO update(@NotNull Long id, @NotNull AppUserDTO appUser);

    AppUserDTO create(@NotNull AppUserDTO appUser);

    void deleteById(@NotNull Long id);

    AppUserDTO findById(@NotNull Long id);

    boolean existsById(@NotNull Long id);
}
