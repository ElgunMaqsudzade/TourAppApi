package az.code.tourappapi.configs;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
public class AppConfig {
    private Long durationHour;
    private Boolean onetimeOnly;
}
