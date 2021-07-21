package az.code.tourappapi.models;

import az.code.tourappapi.models.enums.OrderStatus;
import az.code.tourappapi.models.identifiers.AppUserOrderId;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "appusers_orders")
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AppUserOrder {
    @EmbeddedId
    private AppUserOrderId id = new AppUserOrderId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("appUserId")
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("orderId")
    private Order order;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private boolean archived;
}