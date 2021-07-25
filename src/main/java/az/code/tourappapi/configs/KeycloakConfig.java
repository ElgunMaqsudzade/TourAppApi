package az.code.tourappapi.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak", ignoreInvalidFields = true)
public class KeycloakConfig {
    private String authServerUrl;
    private String realm;
    private String resource;
    private Map<String, Object> credentials;
}
