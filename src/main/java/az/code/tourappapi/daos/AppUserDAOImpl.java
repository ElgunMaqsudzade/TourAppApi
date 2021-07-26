package az.code.tourappapi.daos;

import az.code.tourappapi.annotations.Exists;
import az.code.tourappapi.daos.interfaces.AppUserDAO;
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
    public Optional<AppUser> find(@NotNull Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<AppUser> find(String email) {
        return userRepo.findByEmail(email);
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
