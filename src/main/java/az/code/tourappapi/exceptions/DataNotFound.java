package az.code.tourappapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFound extends RuntimeException {

    public DataNotFound() {
        super("Corresponding data not found in Database");
    }

    public DataNotFound(String message) {
        super(message);
    }
}
