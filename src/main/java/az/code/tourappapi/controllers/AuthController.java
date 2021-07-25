package az.code.tourappapi.controllers;

import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.dtos.SignInDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<AppUserDTO> register(@RequestBody @Valid AppUserDTO appUserDTO) {
        return new ResponseEntity<>(authService.create(appUserDTO),HttpStatus.OK);
    }

    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody @Valid SignInDTO signInDTO) {
        return new ResponseEntity<>(authService.signIn(signInDTO),HttpStatus.OK);
    }
}