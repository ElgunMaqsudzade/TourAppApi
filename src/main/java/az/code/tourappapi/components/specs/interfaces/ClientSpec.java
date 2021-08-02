package az.code.tourappapi.components.specs.interfaces;

import az.code.tourappapi.models.Client;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.NotNull;

public interface ClientSpec {
    Specification<Client> byUserId(@NotNull  Long userId);
    Specification<Client> byClientId(@NotNull  Long clientId);

    Specification<Client> byOfferId(@NotNull  Long offerId);
}
