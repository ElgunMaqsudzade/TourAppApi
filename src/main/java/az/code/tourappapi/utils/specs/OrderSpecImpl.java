package az.code.tourappapi.utils.specs;


import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.models.*;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.enums.OrderStatus;
import az.code.tourappapi.utils.specs.interfaces.OrderSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderSpecImpl implements OrderSpec {
    private final AppConfig conf;

    @Override
    public Specification<Order> afterThan(LocalDateTime date) {
        return (r, q, cb) -> cb.greaterThan(r.get(Order_.CREATE_DATE), date);
    }

    @Override
    public Specification<Order> expired(Boolean value) {
        return (r, q, cb) -> cb.greaterThan(r.get(Order_.CREATE_DATE), LocalDateTime.now().minusHours(conf.getDurationHour()));
    }

    @Override
    public Specification<Order> archive(Boolean value) {
        return (r, q, cb) -> {
            q.distinct(true);
            Join<Order, AppUserOrder> orderJoin = r.join(Order_.APP_USER_ORDERS, JoinType.LEFT);
            return cb.equal(orderJoin.get(AppUserOrder_.ARCHIVED), value);
        };
    }

    @Override
    public Specification<Order> onlyOffered(Boolean value) {
        return (r, q, cb) -> {
            q.distinct(true);
            return value ? cb.isNotEmpty(r.get(Order_.OFFERS)) : cb.conjunction();
        };
    }

    @Override
    public Specification<Order> forUser(AppUser user) {
        return (r, q, cb) -> {
            q.distinct(true);
            Join<Order, AppUserOrder> orderJoin = r.join(Order_.APP_USER_ORDERS, JoinType.LEFT);
            return cb.equal(orderJoin.get(AppUserOrder_.APP_USER), user);
        };
    }

    @Override
    public Specification<Order> exclude(OrderStatus status) {
        return (r, q, cb) -> {
            q.distinct(true);
            Join<Order, AppUserOrder> orderJoin = r.join(Order_.APP_USER_ORDERS, JoinType.LEFT);
            return cb.notEqual(orderJoin.get(AppUserOrder_.STATUS), status);
        };
    }

    @Override
    public Specification<Order> forId(Long orderId) {
        return (r, q, cb) -> cb.equal(r.get(Order_.ID), orderId);
    }
}
