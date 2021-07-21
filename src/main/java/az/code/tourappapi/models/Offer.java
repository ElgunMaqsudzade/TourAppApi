package az.code.tourappapi.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    private Calendar calendar;
    @Size(max = 9)
    @Positive
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser appUser;
}