package az.code.tourappapi.components.specs;

import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.models.Offer;
import az.code.tourappapi.models.Offer_;
import az.code.tourappapi.models.Order_;
import az.code.tourappapi.components.specs.interfaces.OfferSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OfferSpecImpl implements OfferSpec {
    private final AppConfig conf;


    @Override
    public Specification<Offer> expired(Boolean value) {
        return (r, q, cb) -> !value ? cb.greaterThan(r.get(Offer_.ORDER).get(Order_.CREATE_DATE),
                LocalDateTime.now().minusHours(conf.getDurationHour())) : cb.conjunction();
    }


    @Override
    public Specification<Offer> byOrderId(Long orderId) {
        return (r, q, cb) -> orderId != null ? cb.equal(r.get(Offer_.ORDER).get(Order_.ID), orderId) : cb.conjunction();
    }
}
