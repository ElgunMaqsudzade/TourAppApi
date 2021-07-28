package az.code.tourappapi.utils.specs.interfaces;

import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public interface OrderSpec {
    Specification<Order> afterThan(LocalDateTime date);

    Specification<Order> archived(Boolean value, AppUser user);

    Specification<Order> exclude(OrderStatus status);
}
