package az.code.tourappapi.controllers;

import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.dtos.ChangePasswordDTO;
import az.code.tourappapi.models.dtos.SignInDTO;
import az.code.tourappapi.models.enums.TokenType;
import az.code.tourappapi.services.interfaces.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final KeycloakService keycloakService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<AppUserDTO> register(@RequestBody @Valid AppUserDTO appUserDTO) {
        return new ResponseEntity<>(keycloakService.create(appUserDTO), HttpStatus.OK);
    }

    @RequestMapping(path = "/signIn", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody @Valid SignInDTO signInDTO) {
        return new ResponseEntity<>(keycloakService.signIn(signInDTO), HttpStatus.OK);
    }

    @RequestMapping(path = "/send-token", method = RequestMethod.GET)
    public ResponseEntity<?> sendToken(@RequestParam TokenType type, @RequestParam String email) {
        keycloakService.sendToken(email, type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/verify-token", method = RequestMethod.GET)
    public ResponseEntity<?> verifyToken(@RequestParam String token, @RequestParam String email) {
        keycloakService.verifyToken(token, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/forgot-password", method = RequestMethod.POST)
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ChangePasswordDTO passwordDTO) {
        keycloakService.changePassword(passwordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}