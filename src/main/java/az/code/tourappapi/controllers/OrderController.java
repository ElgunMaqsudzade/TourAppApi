package az.code.tourappapi.controllers;

import az.code.tourappapi.configs.RabbitMQConfig;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.dtos.OfferDTO;
import az.code.tourappapi.models.dtos.OrderDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;
import az.code.tourappapi.services.interfaces.OfferService;
import az.code.tourappapi.services.interfaces.OrderService;
import az.code.tourappapi.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController()
@RequestMapping(path = "/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OfferService offerService;
    private final ModelMapperUtil mapperUtil;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(@RequestAttribute("user") AppUser user,
                                       @RequestParam Integer page,
                                       @RequestParam Integer size) {
        return new ResponseEntity<>(orderService.findAll(user, page, size), HttpStatus.OK);
    }


    @RequestMapping(path = "/{id}/offer", method = RequestMethod.GET)
    public ResponseEntity<PaginationDTO<OfferDTO>> getOffers(@RequestAttribute("user") AppUser user,
                                                             @PathVariable Long id,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size) {
        return new ResponseEntity<>(offerService.findAll(user, id, page, size), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/offer", method = RequestMethod.POST)
    public ResponseEntity<OfferDTO> createOffer(@RequestAttribute("user") AppUser user,
                                                @PathVariable Long id,
                                                @RequestBody @Valid OfferDTO offerDTO) {

        return new ResponseEntity<>(offerService.create(user, id, offerDTO), HttpStatus.OK);
    }

    @RequestMapping(path = "/archive", method = RequestMethod.GET)
    public ResponseEntity<PaginationDTO<OrderDTO>> getArchivedOrders(@RequestAttribute("user") AppUser user,
                                                                     @RequestParam Integer page,
                                                                     @RequestParam Integer size) {
        return new ResponseEntity<>(orderService.findAllArchived(user, page, size), HttpStatus.OK);
    }

//    @RequestMapping(path = "/archive/{id}", method = RequestMethod.POST)
//    public ResponseEntity<OrderDTO> archiveOrder(@PathVariable Long id) {
//        return new ResponseEntity<>(orderService.archive(id), HttpStatus.OK);
//    }

    @RabbitListener(queues = RabbitMQConfig.subscription)
    public void saveOrders(Map<String, String> map) {
        orderService.create(mapperUtil.map(map, OrderDTO.class));
    }
}
