package az.code.tourappapi.controllers;


import az.code.tourappapi.models.dtos.ChangePasswordDTO;
import az.code.tourappapi.models.dtos.UpdatePasswordDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.services.interfaces.KeycloakService;
import az.code.tourappapi.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController()
@RequestMapping(path = "/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final OrderService orderService;
    private final AppUserService userService;
    private final KeycloakService keycloakService;


    @RequestMapping(path = "/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getOrders() {
        System.out.println("ds");
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(path = "/update-password", method = RequestMethod.POST)
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordDTO passwordDTO,
                                            @RequestAttribute("email") String email) {
        keycloakService.updatePassword(email, passwordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}