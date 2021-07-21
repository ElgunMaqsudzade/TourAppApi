package az.code.tourappapi.daos.interfaces;

import az.code.tourappapi.models.AppUser;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface AppUserDAO {
    AppUser save(@NotNull AppUser appUser);

    void deleteById(@NotNull Long id);

    Optional<AppUser> findById(@NotNull Long id);

    boolean existsById(@NotNull Long id);
}
