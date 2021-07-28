package az.code.tourappapi.repos;

import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long>, JpaSpecificationExecutor<Token> {
    Optional<Token> findByToken(String token);

}
