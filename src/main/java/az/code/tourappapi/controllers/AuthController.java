package az.code.tourappapi.controllers;

import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class AuthController {
    private final AppUserService userService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<AppUserDTO> register(@RequestBody @Valid AppUserDTO appUserDTO) {
        return new ResponseEntity<>(userService.create(appUserDTO),HttpStatus.OK);
    }
}