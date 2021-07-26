package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.dtos.ChangePasswordDTO;
import az.code.tourappapi.models.dtos.SignInDTO;
import az.code.tourappapi.models.dtos.UpdatePasswordDTO;
import az.code.tourappapi.models.enums.TokenType;
import org.keycloak.representations.AccessTokenResponse;

import java.util.Optional;

public interface KeycloakService {
    AppUserDTO create(AppUserDTO appUserDTO);

    AccessTokenResponse signIn(SignInDTO sign);

    void sendToken(String email, TokenType type);

    Optional<Token> verifyToken(String token, String email);

    boolean verifyEmail(String token, String email);

    void updatePassword(String email, UpdatePasswordDTO passwordDTO);

    void changePassword(ChangePasswordDTO passwordDTO);
}
