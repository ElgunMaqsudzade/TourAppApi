package az.code.tourappapi.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
    private LocalDate travelStartDate;
    private LocalDate travelEndDate;
    private String travellerCount;
    private Double budget;
    private LocalDateTime createDate;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<AppUserOrder> appUserOrders;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers;
}