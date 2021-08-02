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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Order order;

    @OneToOne
    private Offer offer;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private boolean archived;
}