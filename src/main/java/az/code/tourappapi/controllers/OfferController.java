package az.code.tourappapi.controllers;

import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.dtos.OfferDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;
import az.code.tourappapi.services.interfaces.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping(path = "/api/v1/offer")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<PaginationDTO<OfferDTO>> getOffers(@RequestAttribute("user") AppUser user,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size) {
        return new ResponseEntity<>(offerService.findAll(user, page, size), HttpStatus.OK);
    }
}