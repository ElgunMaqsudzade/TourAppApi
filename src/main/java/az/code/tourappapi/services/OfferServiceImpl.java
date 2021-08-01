package az.code.tourappapi.services;

import az.code.tourappapi.components.SchedulerExecutor;
import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.daos.interfaces.OfferDAO;
import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.exceptions.ConflictException;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.exceptions.ForbiddenException;
import az.code.tourappapi.models.*;
import az.code.tourappapi.models.dtos.OfferDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;
import az.code.tourappapi.models.enums.OrderStatus;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.services.interfaces.OfferService;
import az.code.tourappapi.utils.ModelMapperUtil;
import az.code.tourappapi.utils.TimerUtil;
import az.code.tourappapi.utils.specs.interfaces.OfferSpec;
import az.code.tourappapi.utils.specs.interfaces.OrderSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.NotAllowedException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final AppConfig conf;
    private final OrderSpec orderSpec;
    private final OfferSpec offerSpec;
    private final OfferDAO offerDAO;
    private final OrderDAO orderDAO;
    private final AppUserService userService;
    private final ModelMapperUtil mapperUtil;
    private final TimerUtil timerUtil;

    @Override
    public OfferDTO create(@NotNull AppUser user, @NotNull Long orderId, @NotNull OfferDTO offerDTO) {
        if (conf.getOnetimeOnly() && offerDAO.existsByOrderId(orderId))
            throw new ConflictException();

        if (!timerUtil.isAppropriate())
            throw new ForbiddenException();

        Optional<Order> order = orderDAO
                .find(Specification
                        .where(orderSpec.expired(false)
                                .and(orderSpec.forId(orderId))));

        order.ifPresentOrElse(i -> userService.addOrder(user, i, OrderStatus.OFFERED), () -> {
            throw new DataNotFound();
        });

        Offer offer = offerDAO.save(mapperUtil.map(offerDTO, Offer.class)
                .toBuilder()
                .appUser(user)
                .order(order.get())
                .build());


        return mapperUtil.map(offer, OfferDTO.class);
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
        return findAll(user, null, page, size);
    }

    @Override
    public PaginationDTO<OfferDTO> findAll(@NotNull AppUser user, Long orderId, Integer page, Integer size) {
        Specification<Offer> spec = Specification
                .where(offerSpec.byOrderId(orderId)
                        .and(offerSpec.expired(false)));
        Page<Offer> p = offerDAO.findAll(spec, PageRequest.of(page, size));
        return mapperUtil.toPagination(p, OfferDTO.class);
    }
}
