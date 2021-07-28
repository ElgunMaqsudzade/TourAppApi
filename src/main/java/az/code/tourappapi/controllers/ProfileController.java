package az.code.tourappapi.controllers;


import az.code.tourappapi.configs.RabbitMQConfig;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Offer;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.ChangePasswordDTO;
import az.code.tourappapi.models.dtos.OrderDTO;
import az.code.tourappapi.models.dtos.PaginationDTO;
import az.code.tourappapi.models.dtos.UpdatePasswordDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.services.interfaces.KeycloakService;
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
@RequestMapping(path = "/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final KeycloakService keycloakService;


    @RequestMapping(path = "/update-password", method = RequestMethod.POST)
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordDTO passwordDTO,
                                            @RequestAttribute("user") AppUser user) {
        keycloakService.updatePassword(user.getEmail(), passwordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}