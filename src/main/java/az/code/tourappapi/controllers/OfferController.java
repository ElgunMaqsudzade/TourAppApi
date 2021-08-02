package az.code.tourappapi.controllers;

import az.code.tourappapi.components.SchedulerExecutor;
import az.code.tourappapi.configs.RabbitMQConfig;
import az.code.tourappapi.daos.interfaces.ClientDAO;
import az.code.tourappapi.daos.interfaces.OfferDAO;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Client;
import az.code.tourappapi.models.dtos.ClientDTO;
import az.code.tourappapi.models.dtos.OfferDTO;
import az.code.tourappapi.models.dtos.OrderDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;
import az.code.tourappapi.services.interfaces.ClientService;
import az.code.tourappapi.services.interfaces.OfferService;
import az.code.tourappapi.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController()
@RequestMapping(path = "/api/v1/offer")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;
    private final ClientService clientService;
    private final ModelMapperUtil mapperUtil;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<PaginationDTO<OfferDTO>> getOffers(@RequestAttribute("user") AppUser user,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size) {
        return new ResponseEntity<>(offerService.findAll(user, page, size), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}/client", method = RequestMethod.GET)
    public ResponseEntity<ClientDTO> getClientInfo(@RequestAttribute("user") AppUser user,
                                                                  @PathVariable Long id) {
        return new ResponseEntity<>(clientService.find(user, id), HttpStatus.OK);
    }

    @RabbitListener(queues = RabbitMQConfig.offerReply)
    public void saveOrders(ClientDTO clientDTO) {
        clientService.create(clientDTO);
    }
}