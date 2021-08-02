package az.code.tourappapi.daos;

import az.code.tourappapi.daos.interfaces.OfferDAO;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.Offer;
import az.code.tourappapi.repos.OfferRepo;
import az.code.tourappapi.components.specs.interfaces.OfferSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OfferDAOImpl implements OfferDAO {
    private final OfferRepo offerRepo;
    private final OfferSpec offerSpec;

    @Override
    public Offer save(@NotNull Offer offer) {
        return offerRepo.save(offer);
    }

    @Override
    public void delete(@NotNull Long id) {
        offerRepo.deleteById(id);
    }

    @Override
    public Offer find(Long id) {
        Optional<Offer> offer = offerRepo.findById(id);
        if (offer.isEmpty()) throw new DataNotFound("Offer not found in database");

        return offer.get();
    }

    @Override
    public Offer find(@NotNull Long id, @NotNull Long userId) {
        Optional<Offer> offer = offerRepo.findByAppUserIdAndId(userId, id);
        if (offer.isEmpty()) throw new DataNotFound("Offer not found in database");

        return offer.get();
    }

    @Override
    public Page<Offer> findAll(@NotNull Specification<Offer> spec, Pageable pageable) {
        return offerRepo.findAll(spec, pageable);
    }

    @Override
    public Page<Offer> findAll(@NotNull Pageable pageable) {
        return offerRepo.findAll(pageable);
    }

    @Override
    public boolean existsByOrderId(@NotNull Long orderId) {
        return offerRepo.findAll(offerSpec.byOrderId(orderId), PageRequest.of(0, 1))
                .stream()
                .findAny()
                .isPresent();
    }
}
