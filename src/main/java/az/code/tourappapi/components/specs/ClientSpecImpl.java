package az.code.tourappapi.components.specs;

import az.code.tourappapi.components.specs.interfaces.ClientSpec;
import az.code.tourappapi.models.AppUser_;
import az.code.tourappapi.models.Client;
import az.code.tourappapi.models.Client_;
import az.code.tourappapi.models.Offer_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ClientSpecImpl implements ClientSpec {
    @Override
    public Specification<Client> byUserId(Long userId) {
        return (r, q, cb) -> cb.equal(r.get(Client_.OFFER).get(Offer_.APP_USER).get(AppUser_.ID), userId);
    }

    @Override
    public Specification<Client> byClientId(Long clientId) {
        return (r, q, cb) -> cb.equal(r.get(Client_.ID), clientId);
    }

    @Override
    public Specification<Client> byOfferId(Long offerId) {
        return (r, q, cb) -> cb.equal(r.get(Client_.OFFER).get(Offer_.ID), offerId);
    }
}
