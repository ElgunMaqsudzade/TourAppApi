package az.code.tourappapi.services;

import az.code.tourappapi.components.SchedulerExecutor;
import az.code.tourappapi.configs.KeycloakConfig;
import az.code.tourappapi.daos.interfaces.AppUserDAO;
import az.code.tourappapi.exceptions.BadRequestException;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.utils.KeycloakUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserDAO userDAO;
    private final Keycloak keycloak;
    private final KeycloakConfig conf;
    private final ObjectMapper mapper;
    private final SchedulerExecutor sch;


    @Override
    public AppUserDTO update(@NotNull Long id, @NotNull AppUserDTO appUser) {
        AppUser user = mapper.convertValue(appUser, AppUser.class);
        return mapper.convertValue(userDAO.save(user.toBuilder().id(id).build()), AppUserDTO.class);
    }

    @Override
    public AppUserDTO create(@NotNull AppUserDTO appUser) {
        AppUser user = mapper.convertValue(appUser, AppUser.class);

        UserRepresentation userRep = KeycloakUtil.userRepresentation(appUser, conf);
        RealmResource realmResource = keycloak.realm(conf.getAdminRealm().getName());
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(userRep);

        if (response.getStatus() == 201) {
            userDAO.save(user);
        } else
            throw new BadRequestException(response.getStatusInfo().toString());

        keycloak.close();
        return appUser;
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
