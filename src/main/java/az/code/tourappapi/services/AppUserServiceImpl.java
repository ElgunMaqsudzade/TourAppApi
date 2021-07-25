package az.code.tourappapi.services;

import az.code.tourappapi.components.SchedulerExecutor;
import az.code.tourappapi.configs.KeycloakAdminConfig;
import az.code.tourappapi.configs.KeycloakConfig;
import az.code.tourappapi.daos.interfaces.AppUserDAO;
import az.code.tourappapi.exceptions.BadRequestException;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserDAO userDAO;
    private final ObjectMapper mapper;

    @Override
    public AppUserDTO update(@NotNull Long id, @NotNull AppUserDTO appUser) {
        AppUser user = mapper.convertValue(appUser, AppUser.class);
        return mapper.convertValue(userDAO.save(user.toBuilder().id(id).build()), AppUserDTO.class);
    }

    @Override
    public AppUserDTO create(@NotNull AppUserDTO appUser) {
        AppUser user = mapper.convertValue(appUser, AppUser.class);
        return mapper.convertValue(userDAO.save(user), AppUserDTO.class);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public AppUserDTO findById(@NotNull Long id) {
        Optional<AppUser> appUser = userDAO.findById(id);
        if (appUser.isEmpty()) throw new DataNotFound("User not found in database");

        return mapper.convertValue(appUser.get(), AppUserDTO.class);
    }

    @Override
    public boolean existsById(@NotNull Long id) {
        return userDAO.existsById(id);
    }

}
