package az.code.tourappapi.daos.interfaces;

import az.code.tourappapi.models.Offer;
import az.code.tourappapi.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface OfferDAO {
    Offer save(@NotNull Offer offer);

    void delete(@NotNull Long id);

    Offer find(@NotNull Long id, @NotNull Long userId);

    boolean existsByOrderId(@NotNull Long orderId);

    Page<Offer> findAll(@NotNull Specification<Offer> spec, Pageable pageable);

    Page<Offer> findAll(@NotNull Pageable pageable);
}
