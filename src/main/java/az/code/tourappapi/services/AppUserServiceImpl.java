package az.code.tourappapi.services;


import az.code.tourappapi.daos.interfaces.AppUserDAO;
import az.code.tourappapi.exceptions.ConflictException;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.AppUser_;
import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
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
        if (!isNew(appUser)) throw new ConflictException();
        AppUser user = mapper.convertValue(appUser, AppUser.class);
        return mapper.convertValue(userDAO.save(user), AppUserDTO.class);
    }

    @Override
    public void delete(@NotNull Long id) {
        userDAO.delete(id);
    }

    @Override
    public AppUserDTO find(@NotNull Long id) {
        Optional<AppUser> appUser = userDAO.find(id);
        if (appUser.isEmpty()) throw new DataNotFound("User not found in database");

        return mapper.convertValue(appUser.get(), AppUserDTO.class);
    }

    @Override
    public AppUser find(String email) {
        Optional<AppUser> appUser = userDAO.find(email);
        if (appUser.isEmpty()) throw new DataNotFound("User not found in database");

        return appUser.get();
    }

    @Override
    public boolean exists(@NotNull Long id) {
        return userDAO.exists(id);
    }

    @Override
    public boolean isNew(AppUserDTO appUserDTO) {
        Specification<AppUser> spec = ((r, q, cb) ->
                cb.and(cb.equal(r.get(AppUser_.COMPANY),appUserDTO.getCompany()),
                        cb.equal(r.get(AppUser_.EMAIL),appUserDTO.getEmail())));

        return userDAO.findAll(spec).isEmpty();
    }

}
