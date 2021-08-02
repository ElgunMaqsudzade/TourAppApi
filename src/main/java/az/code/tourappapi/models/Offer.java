package az.code.tourappapi.models;

import az.code.tourappapi.annotations.Length;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Calendar;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 1000)
    private String description;
    @Size(max = 1000)
    private String locations;
    @NotNull
    private LocalDateTime travelStartDate;
    @NotNull
    private LocalDateTime travelEndDate;
    @Length(max = 9)
    @Positive
    private Double price;
    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL)
    private Client client;
    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL)
    private AppUserOrder appUserOrder;
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser appUser;
    @ManyToOne
    private Order order;
}