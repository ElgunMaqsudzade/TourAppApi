package az.code.tourappapi;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TourAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourAppApiApplication.class, args);
    }

}
