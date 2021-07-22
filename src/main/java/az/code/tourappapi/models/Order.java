package az.code.tourappapi.models;

import az.code.tourappapi.annotations.Exists;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telegramIdentifier;
    private String language;
    private String tourType;
    private String addressFrom;
    private String addressTo;
    private LocalTime travelStartDate;
    private LocalTime travelEndDate;
    private String travellerCount;
    private Double budget;
    private LocalDateTime expireTime;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AppUserOrder> appUserOrders;
}