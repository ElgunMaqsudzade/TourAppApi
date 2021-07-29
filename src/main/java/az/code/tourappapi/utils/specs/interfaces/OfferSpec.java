package az.code.tourappapi.utils.specs.interfaces;

import az.code.tourappapi.models.Offer;
import org.springframework.data.jpa.domain.Specification;

public interface OfferSpec {

    Specification<Offer> expired(Boolean value);

    Specification<Offer> byOrderId(Long orderId);
}
