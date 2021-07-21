package az.code.tourappapi.exceptions.handlers;


import az.code.tourappapi.utils.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConflict(ConstraintViolationException ex) {
        return new ResponseEntity<>(ExceptionUtils.exceptionMessage(ex), HttpStatus.BAD_REQUEST);
    }
}