package az.code.tourappapi.daos;

import az.code.tourappapi.daos.interfaces.AppUserDAO;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.repos.AppUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppUserDAOImpl implements AppUserDAO {
    private final AppUserRepo userRepo;

    @Override
    public AppUser save(AppUser appUser) {
        return userRepo.save(appUser);
    }

    @Override
    public void delete(@NotNull Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public AppUser find(@NotNull Long id) {
        Optional<AppUser> opUser = userRepo.findById(id);
        if (opUser.isEmpty()) throw new DataNotFound("User not found in database");

        return opUser.get();
    }

    @Override
    public AppUser find(String email) {
        Optional<AppUser> opUser = userRepo.findByEmail(email);
        if (opUser.isEmpty()) throw new DataNotFound("User not found in database");

        return opUser.get();
    }

    @Override
    public List<AppUser> findAll(@Nullable Specification<AppUser> spec) {
        return userRepo.findAll(spec);
    }

    @Override
    public boolean exists(@NotNull Long id) {
        return userRepo.existsById(id);
    }
}
