package az.code.tourappapi.utils.specs;


import az.code.tourappapi.models.*;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.enums.OrderStatus;
import az.code.tourappapi.utils.specs.interfaces.OrderSpec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;

@Component
public class OrderSpecImpl implements OrderSpec {
    @Override
    public Specification<Order> afterThan(LocalDateTime date) {
        return (r, q, cb) -> cb.greaterThan(r.get(Order_.CREATE_DATE), date);
    }

    @Override
    public Specification<Order> archived(Boolean value, AppUser user) {
        return (r, q, cb) -> {
            q.distinct(true);
            Join<Order, AppUserOrder> orderJoin = r.join(Order_.APP_USER_ORDERS, JoinType.LEFT);
            Predicate archived = cb.equal(orderJoin.get(AppUserOrder_.ARCHIVED), value);
            Predicate appUser = cb.equal(orderJoin.get(AppUserOrder_.APP_USER), user);
            Predicate alwaysTrue = cb.and(archived, appUser);

            Predicate nullable = cb.isNull(orderJoin.get(AppUserOrder_.ID));

            return cb.or(alwaysTrue, nullable);
        };
    }

    @Override
    public Specification<Order> exclude(OrderStatus status) {
        return (r, q, cb) -> {
            q.distinct(true);
            Join<Order, AppUserOrder> orderJoin = r.join(Order_.APP_USER_ORDERS, JoinType.LEFT);
            Predicate nullable = cb.isNull(orderJoin.get(AppUserOrder_.ID));

            return cb.or(cb.notEqual(orderJoin.get(AppUserOrder_.STATUS), status), nullable);
        };
    }
}
