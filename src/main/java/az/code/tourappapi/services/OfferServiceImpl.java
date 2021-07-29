package az.code.tourappapi.services;

import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.daos.interfaces.OfferDAO;
import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.exceptions.ConflictException;
import az.code.tourappapi.models.*;
import az.code.tourappapi.models.dtos.OfferDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.services.interfaces.OfferService;
import az.code.tourappapi.utils.ModelMapperUtil;
import az.code.tourappapi.utils.specs.interfaces.SpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final AppConfig conf;
    private final SpecService specService;
    private final OfferDAO offerDAO;
    private final OrderDAO orderDAO;
    private final AppUserService userService;
    private final ModelMapperUtil mapperUtil;

    @Override
    public OfferDTO create(@NotNull AppUser user, @NotNull Long orderId, @NotNull OfferDTO offerDTO) {
        if (offerDAO.existsByOrderId(orderId) && conf.getOnetimeOnly())
            throw new ConflictException();

        Offer offer = mapperUtil.map(offerDTO, Offer.class);
        Order order = orderDAO.find(orderId);
        userService.addOrder(user, order);
        return mapperUtil.map(offerDAO.save(offer.toBuilder().appUser(user).order(order).build()), OfferDTO.class);
    }

    @Override
    public OfferDTO update(@NotNull AppUser user, @NotNull Long offerId, @NotNull OfferDTO offerDTO) {
        Offer offer = offerDAO.find(offerId, user.getId());
        mapperUtil.map(offerDTO, offer);

        return mapperUtil.map(offerDAO.save(offer), OfferDTO.class);
    }

    @Override
    public void delete(@NotNull AppUser user, @NotNull Long id) {
        offerDAO.delete(id);
    }

    @Override
    public OfferDTO find(@NotNull AppUser user, @NotNull Long id) {
        return mapperUtil.map(offerDAO.find(id, user.getId()), OfferDTO.class);
    }

    @Override
    public PaginationDTO<OfferDTO> findAll(@NotNull AppUser user, Integer page, Integer size) {
        Page<Offer> p = offerDAO.findAll(PageRequest.of(page, size));
        return mapperUtil.toPagination(p, OfferDTO.class);
    }

    @Override
    public PaginationDTO<OfferDTO> findAll(@NotNull AppUser user,@NotNull Long orderId, Integer page, Integer size) {
        Specification<Offer> spec = specService.byOrderId(orderId);
        Page<Offer> p = offerDAO.findAll(spec, PageRequest.of(page, size));
        return mapperUtil.toPagination(p, OfferDTO.class);
    }
}
