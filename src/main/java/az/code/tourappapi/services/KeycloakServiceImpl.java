package az.code.tourappapi.services;

import az.code.tourappapi.components.SchedulerExecutor;
import az.code.tourappapi.configs.KeycloakConfig;
import az.code.tourappapi.daos.interfaces.TokenDAO;
import az.code.tourappapi.exceptions.BadRequestException;
import az.code.tourappapi.exceptions.ConflictException;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.dtos.SignInDTO;
import az.code.tourappapi.models.enums.TokenType;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.services.interfaces.KeycloakService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final AppUserService userService;
    private final TokenDAO tokenDAO;
    private final KeycloakConfig conf;
    private final ObjectMapper mapper;
    private final SchedulerExecutor sch;


    private String realm;
    private String authUrl;
    private String clientId;
    private String initialRole;
    private String standardRole;
    private Map<String, Object> credentials;

    @PostConstruct
    public void setRealmProps() {
        this.initialRole = conf.getRoles().getInitial();
        this.standardRole = conf.getRoles().getStandard();
        this.realm = conf.getProps().getRealm();
        this.authUrl = conf.getProps().getAuthServerUrl();
        this.clientId = conf.getProps().getResource();
        this.credentials = conf.getProps().getCredentials();
    }

    @Override
    public AppUserDTO create(AppUserDTO appUserDTO) {
        UserRepresentation userRep = mapper.convertValue(appUserDTO, UserRepresentation.class);
        userRep.setUsername(appUserDTO.getEmail());
        userRep.setEnabled(true);

        RealmResource realmResource = conf.getInstance().realm(realm);
        RolesResource rolesResource = realmResource.roles();
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(userRep);

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
        Configuration configuration = new Configuration(authUrl, realm, clientId, credentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        try {
            return authzClient.obtainAccessToken(sign.getEmail(), sign.getPassword());
        } catch (Exception ex) {
            throw new BadRequestException("User not found");
        }
    }


    @Override
    public void sendToken(String email, TokenType type) {
        AppUser appUser = userService.find(email);
        Token token = Token.builder().type(type).token(UUID.randomUUID().toString()).appUser(appUser).build();
        tokenDAO.save(token);
        sch.runEmailVerification(token);
    }

    @Override
    public boolean verifyToken(String token, String email) {
        Optional<Token> optionalToken = tokenDAO.find(email, token);
        if (optionalToken.isPresent()) {
            RealmResource realmResource = conf.getInstance().realm(realm);
            RolesResource rolesResource = realmResource.roles();
            RoleRepresentation initial = rolesResource.get(initialRole).toRepresentation();
            RoleRepresentation standard = rolesResource.get(standardRole).toRepresentation();
            UserRepresentation userRep = realmResource.users().search(email).get(0);
            userRep.setEmailVerified(true);
            UserResource ur = realmResource.users().get(realmResource.users().search(email).get(0).getId());
            ur.roles().realmLevel().remove(Collections.singletonList(initial));
            ur.roles().realmLevel().add(Collections.singletonList(standard));
            ur.update(userRep);
            tokenDAO.delete(optionalToken.get().getId());
            return true;
        }
        return false;
    }



    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

}
