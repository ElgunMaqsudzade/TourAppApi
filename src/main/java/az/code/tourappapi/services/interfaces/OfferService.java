package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.dtos.OfferDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;

import javax.validation.constraints.NotNull;

public interface OfferService {
    OfferDTO create(@NotNull AppUser user, @NotNull Long orderId, @NotNull OfferDTO offerDTO);

    OfferDTO update(@NotNull AppUser user, @NotNull Long offerId, @NotNull OfferDTO offerDTO);

    void delete(@NotNull AppUser user, @NotNull Long id);

    OfferDTO find(@NotNull AppUser user, @NotNull Long id);

    PaginationDTO<OfferDTO> findAll(@NotNull AppUser user, Integer page, Integer size);

    PaginationDTO<OfferDTO> findAll(@NotNull AppUser user, @NotNull Long orderId, Integer page, Integer size);
}
