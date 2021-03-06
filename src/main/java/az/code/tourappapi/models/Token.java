package az.code.tourappapi.models;

import az.code.tourappapi.models.enums.TokenType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private String token;
    private boolean verified;
    @ManyToOne(optional = false)
    private AppUser appUser;
}
