package az.code.tourappapi.repos;

import az.code.tourappapi.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
