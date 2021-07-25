package az.code.tourappapi.services;

import az.code.tourappapi.components.SchedulerExecutor;
import az.code.tourappapi.configs.KeycloakAdminConfig;
import az.code.tourappapi.configs.KeycloakConfig;
import az.code.tourappapi.exceptions.BadRequestException;
import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.dtos.SignInDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.services.interfaces.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserService userService;
    private final SchedulerExecutor sch;
    private final Keycloak keycloak;
    private final KeycloakAdminConfig conf;
    private final KeycloakConfig config;
    private final ObjectMapper mapper;


    private String productRealm;
    private String authUrl;
    private String clientId;
    private String initialRole;
    private Map<String, Object> credentials;

    @PostConstruct
    public void setRealmProps() {
        this.productRealm = config.getRealm();
        this.initialRole = conf.getProductRealm().getRoles().getInitial();
        this.authUrl = config.getAuthServerUrl();
        this.clientId = config.getResource();
        this.credentials = config.getCredentials();
    }

    @Override
    public AppUserDTO create(AppUserDTO appUserDTO) {
        UserRepresentation userRepresentation = userRepresentation(appUserDTO);

        RealmResource realmResource = keycloak.realm(productRealm);
        RolesResource rolesResource = realmResource.roles();
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            CredentialRepresentation passwordCred = createPasswordCredentials(appUserDTO.getPassword());

            UserResource userResource = usersResource.get(userId);
            RoleRepresentation realmRoleUser = rolesResource.get(initialRole).toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(realmRoleUser));
            // Set password credential
            userResource.resetPassword(passwordCred);
            //create newUser for database
            userService.create(appUserDTO);
        } else
            throw new BadRequestException(response.getStatusInfo().toString());

        return appUserDTO;
    }

    @Override
    public AccessTokenResponse signIn(SignInDTO sign) {
        credentials.put(OAuth2Constants.GRANT_TYPE, OAuth2Constants.PASSWORD);
        Configuration configuration = new Configuration(authUrl, productRealm, clientId, credentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        try {
            return authzClient.obtainAccessToken(sign.getEmail(), sign.getPassword());
        } catch (Exception ex) {
            throw new BadRequestException("User not found");
        }
    }


    private UserRepresentation userRepresentation(AppUserDTO user) {
        UserRepresentation userRep = mapper.convertValue(user, UserRepresentation.class);
        userRep.setUsername(user.getEmail());
        userRep.setEnabled(true);
        return userRep;
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

}
