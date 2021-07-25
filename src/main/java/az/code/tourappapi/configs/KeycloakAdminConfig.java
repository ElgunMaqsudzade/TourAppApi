package az.code.tourappapi.configs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak-server", ignoreInvalidFields = true)
public class KeycloakAdminConfig {
    private AdminRealm adminRealm;
    private ProductRealm productRealm;

    @Data
    public static class ProductRealm {
        private String name;
        private Roles roles;

        @Data
        public static class Roles {
            private String initial;
            private String standard;
        }
    }
    @Data
    public static class AdminRealm {
        private String serverUrl;
        private String name;
        private String clientId;
        private String username;
        private String password;
    }

    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(adminRealm.serverUrl)
                .realm(adminRealm.name)
                .grantType(OAuth2Constants.PASSWORD)
                .username(adminRealm.username)
                .password(adminRealm.password)
                .clientId(adminRealm.clientId)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
    }
}