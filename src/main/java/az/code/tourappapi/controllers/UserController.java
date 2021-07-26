package az.code.tourappapi.controllers;

import az.code.tourappapi.components.SchedulerExecutor;
import az.code.tourappapi.models.enums.TokenType;
import az.code.tourappapi.services.interfaces.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final KeycloakService keycloakService;


    @RequestMapping(path = "/send-token", method = RequestMethod.GET)
    public ResponseEntity<?> sendToken(@RequestParam TokenType type, @RequestAttribute("email") String email) {
        keycloakService.sendToken(email, type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/verify", method = RequestMethod.GET)
    public ResponseEntity<Boolean> verifyToken(@RequestParam String token, @RequestAttribute("email") String email) {
        return new ResponseEntity<>(keycloakService.verifyToken(token, email), HttpStatus.OK);
    }
}
