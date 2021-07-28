package az.code.tourappapi.repos;

import az.code.tourappapi.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OfferRepo extends JpaRepository<Offer, Long> , JpaSpecificationExecutor<Offer> {
    Optional<Offer> findByAppUserIdAndId(Long appUser_id, Long id);
}
