package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.dtos.SignInDTO;
import org.keycloak.representations.AccessTokenResponse;

public interface AuthService {
    AppUserDTO create(AppUserDTO appUserDTO);

    AccessTokenResponse signIn(SignInDTO sign);
}
