package az.code.tourappapi.configs;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "keycloak-admin", ignoreInvalidFields = true)
public class KeycloakConfig {
    private final KeycloakSpringBootProperties props;
    private String realm;
    private String clientId;
    private String username;
    private String password;
    private Roles roles;

    @Data
    public static class Roles {
        private String initial;
        private String standard;
    }

    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(props.getAuthServerUrl())
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .username(username)
                .password(password)
                .clientId(clientId)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
    }
}
