package az.code.tourappapi.repos;

import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Transactional
    @Modifying
    @Query("UPDATE appusers_orders ao SET ao.archived=:archive where ao.order.id=:orderId and ao.appUser.id=:userId")
    void archive(Long userId, Long orderId, Boolean archive);

}
