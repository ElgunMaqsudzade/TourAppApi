package az.code.tourappapi.services;

import az.code.tourappapi.daos.interfaces.ClientDAO;
import az.code.tourappapi.daos.interfaces.OfferDAO;
import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Client;
import az.code.tourappapi.models.Offer;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.ClientDTO;
import az.code.tourappapi.models.enums.OrderStatus;
import az.code.tourappapi.repos.OrderRepo;
import az.code.tourappapi.services.interfaces.ClientService;
import az.code.tourappapi.services.interfaces.OrderService;
import az.code.tourappapi.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientDAO clientDAO;
    private final OfferDAO offerDAO;
    private final OrderDAO orderDAO;
    private final ModelMapperUtil mapper;

    @Override
    public ClientDTO create(@NotNull ClientDTO clientDTO) {
        Offer offer = offerDAO.find(clientDTO.getOfferId());
        Client client = clientDAO.save(mapper.map(clientDTO, Client.class).toBuilder().offer(offer).build());

        Order order = offer.getOrder();
        order.getAppUserOrders()
                .parallelStream()
                .filter(e -> e.getOffer() != null && e.getOffer().getId().equals(offer.getId()))
                .forEach(e -> e.setStatus(OrderStatus.ACCEPTED));

        orderDAO.save(order);
        return mapper.map(client, ClientDTO.class);
    }

    @Override
    public ClientDTO find(AppUser user, Long offerId) {
        Client client = clientDAO.findByOfferId(offerId, user.getId());
        return mapper.map(client, ClientDTO.class);
    }
}
