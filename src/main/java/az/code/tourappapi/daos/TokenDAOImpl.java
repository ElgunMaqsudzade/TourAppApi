package az.code.tourappapi.daos;

import az.code.tourappapi.daos.interfaces.TokenDAO;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.AppUser_;
import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.Token_;
import az.code.tourappapi.models.enums.TokenType;
import az.code.tourappapi.repos.TokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenDAOImpl implements TokenDAO {
    private final TokenRepo tokenRepo;

    @Override
    public Token save(@NotNull Token token) {
        AppUser appUser = token.getAppUser();
        Optional<Token> opToken = find(appUser.getEmail(), token.getType());
        opToken.ifPresent(t -> token.setId(t.getId()));
        return tokenRepo.save(token);
    }

    @Override
    public void delete(@NotNull Long id) {
        if (exists(id))
            tokenRepo.deleteById(id);
        else log.warn("Token doesn't exists");
    }

    @Override
    public Optional<Token> find(@NotNull Long id) {
        return tokenRepo.findById(id);
    }

    @Override
    public Optional<Token> find(@NotNull String email, @NotNull TokenType tokenType) {
        Specification<Token> spec = (r, q, cb) ->
                cb.and(cb.equal(r.get(Token_.APP_USER).get(AppUser_.EMAIL), email),
                        cb.equal(r.get(Token_.TYPE), tokenType));

        return tokenRepo.findAll(spec).stream().findAny();
    }

    @Override
    public Optional<Token> find(@NotNull String email, @NotNull String token) {
        Specification<Token> spec = (r, q, cb) ->
                cb.and(cb.equal(r.get(Token_.APP_USER).get(AppUser_.EMAIL), email),
                cb.equal(r.get(Token_.TOKEN), token));

        return tokenRepo.findAll(spec).stream().findAny();
    }

    @Override
    public Optional<Token> verified(@NotNull String email, @NotNull TokenType type) {
        Optional<Token> optionalToken = find(email, type);
        return optionalToken.filter(Token::isVerified);
    }

    @Override
    public boolean exists(@NotNull Long id) {
        return tokenRepo.existsById(id);
    }
}
