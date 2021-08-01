package az.code.tourappapi.configs;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
public class AppConfig {
    private Long durationHour;
    private Boolean onetimeOnly;
    private WorkHours workHours;

    @Data
    public static class WorkHours{
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime start;
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime end;
    }
}
