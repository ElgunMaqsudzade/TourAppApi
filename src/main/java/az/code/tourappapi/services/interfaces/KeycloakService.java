package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.dtos.ChangePasswordDTO;
import az.code.tourappapi.models.dtos.SignInDTO;
import az.code.tourappapi.models.dtos.UpdatePasswordDTO;
import az.code.tourappapi.models.enums.TokenType;
import org.keycloak.representations.AccessTokenResponse;

public interface KeycloakService {
    AppUserDTO create(AppUserDTO appUserDTO);

    AccessTokenResponse signIn(SignInDTO sign);

    void sendToken(String email, TokenType type);

    boolean verifyToken(String token, String email);

    void updatePassword(String email, UpdatePasswordDTO passwordDTO);
}
