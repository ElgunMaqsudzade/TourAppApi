package az.code.tourappapi.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

public class ExceptionUtils {
    public static Map<String, Object> getFailedResponse(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .parallel()
                .collect(Collectors.toMap(i -> i.getPropertyPath().toString(), ConstraintViolation::getMessage));
    }
    public static Map<String, Object> getFailedResponse(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .parallel()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
    }
}
