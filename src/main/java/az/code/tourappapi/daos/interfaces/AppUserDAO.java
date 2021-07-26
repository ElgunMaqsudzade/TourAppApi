package az.code.tourappapi.daos.interfaces;

import az.code.tourappapi.models.AppUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface AppUserDAO {
    AppUser save(@NotNull AppUser appUser);

    void delete(@NotNull Long id);

    Optional<AppUser> find(@NotNull Long id);

    Optional<AppUser> find(@NotNull String email);

    List<AppUser> findAll(@Nullable Specification<AppUser> spec);

    boolean exists(@NotNull Long id);
}
