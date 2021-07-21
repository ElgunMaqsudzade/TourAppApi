package az.code.tourappapi.models.identifiers;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AppUserOrderId implements Serializable {

    @Column(name = "app_user_id")
    private Long appUserId;

    @Column(name = "order_id")
    private Long orderId;
}