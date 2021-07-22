package az.code.tourappapi.utils;

import az.code.tourappapi.configs.KeycloakConfig;
import az.code.tourappapi.models.dtos.AppUserDTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class KeycloakUtil {

    public static UserRepresentation userRepresentation(AppUserDTO user, KeycloakConfig conf) {
        UserRepresentation userRep = new UserRepresentation();
        HashMap<String, List<String>> clientRoles = new HashMap<>();
        clientRoles.put(conf.getProductRealm().getName(), Collections.singletonList(conf.getProductRealm().getRoles().getInitial()));
        CredentialRepresentation credentials = createPasswordCredentials(user.getPassword());
        userRep.setUsername(user.getEmail());
        userRep.setClientRoles(clientRoles);
        userRep.setCredentials(Collections.singletonList(credentials));
        userRep.setEnabled(true);
        return userRep;
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
