package az.code.tourappapi.daos;

import az.code.tourappapi.annotations.Exists;
import az.code.tourappapi.daos.interfaces.AppUserDAO;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.repos.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppUserDAOImpl implements AppUserDAO {
    private final AppUserRepo userRepo;

    @Override
    public AppUser save(AppUser appUser) {
        return userRepo.save(appUser);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        if (existsById(id))
            userRepo.deleteById(id);
    }

    @Override
    public Optional<AppUser> findById(@NotNull Long id) {
        return userRepo.findById(id);
    }

    @Override
    public boolean existsById(@NotNull Long id) {
        return userRepo.existsById(id);
    }
}
