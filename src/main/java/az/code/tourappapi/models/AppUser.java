package az.code.tourappapi.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "appusers")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 30)
    private String company;
    @NotBlank
    @Size(max = 30)
    private String firstName;
    @NotBlank
    @Size(max = 30)
    private String lastName;
    @Max(value = 10)
    private Integer tin;
    @Email()
    @NotBlank(message = "Shouldn't be blank")
    @Size(max = 30)
    private String email;
    private LocalDateTime createDate;
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Offer> offers;
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<AppUserOrder> appUserOrders;

}