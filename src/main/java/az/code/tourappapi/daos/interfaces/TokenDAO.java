package az.code.tourappapi.daos.interfaces;

import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.enums.TokenType;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface TokenDAO {
    Token save(@NotNull Token token);

    void delete(@NotNull Long id);

    Optional<Token> find(@NotNull Long id);

    Optional<Token> find(@NotNull String email, @NotNull TokenType tokenType);

    Optional<Token> find(@NotNull String email, @NotNull String token);

    boolean exists(@NotNull Long id);
}
