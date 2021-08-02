package az.code.tourappapi.components.specs.interfaces;

import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public interface OrderSpec {
    Specification<Order> afterThan(LocalDateTime date);

    Specification<Order> expired(Boolean value);

    Specification<Order> archive(Boolean value);

    Specification<Order> onlyOffered(Boolean value);


    Specification<Order> forUser(AppUser user);

    Specification<Order> forId(Long orderId);

    Specification<Order> exclude(OrderStatus status);
}
