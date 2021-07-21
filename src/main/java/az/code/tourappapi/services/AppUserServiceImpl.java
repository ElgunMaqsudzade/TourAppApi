package az.code.tourappapi.services;

import az.code.tourappapi.daos.interfaces.AppUserDAO;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.services.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserDAO userDAO;

    @Override
    public AppUser update(@NotNull Long id, @NotNull AppUser appUser) {
        return userDAO.save(appUser);
    }

    @Override
    public AppUser create(@NotNull AppUser appUser) {
        return userDAO.save(appUser);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public AppUser findById(@NotNull Long id) {
        Optional<AppUser> appUser = userDAO.findById(id);
        if (appUser.isEmpty()) throw new DataNotFound("User not found in database");
        return appUser.get();
    }

    @Override
    public boolean existsById(@NotNull Long id) {
        return userDAO.existsById(id);
    }
}
