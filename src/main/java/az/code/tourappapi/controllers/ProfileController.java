package az.code.tourappapi.controllers;



import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping(path = "/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final OrderService orderService;
    private final AppUserService userService;


    @RequestMapping(path = "/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getOrders() {
        System.out.println("ds");
        return new ResponseEntity(HttpStatus.OK);
    }
}