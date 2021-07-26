package az.code.tourappapi.repos;

import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long>, JpaSpecificationExecutor<Token> {
}
