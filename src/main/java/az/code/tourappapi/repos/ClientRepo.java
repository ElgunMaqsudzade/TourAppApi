package az.code.tourappapi.repos;

import az.code.tourappapi.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long> , JpaSpecificationExecutor<Client> {
    Optional<Client> findById(Long aLong);
}
