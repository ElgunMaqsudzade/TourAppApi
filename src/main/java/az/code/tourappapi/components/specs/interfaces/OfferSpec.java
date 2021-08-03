package az.code.tourappapi.components.specs.interfaces;

import az.code.tourappapi.models.Offer;
import org.springframework.data.jpa.domain.Specification;

public interface OfferSpec {

    Specification<Offer> expired(Boolean value);

    Specification<Offer> byUserId(Long userId);

    Specification<Offer> byOrderId(Long orderId);
}
