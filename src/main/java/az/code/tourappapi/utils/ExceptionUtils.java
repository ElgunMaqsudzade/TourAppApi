package az.code.tourappapi.utils;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public class ExceptionUtils {
    public static String exceptionMessage(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .parallel()
                .map(i -> String.format("%s -> %s", i.getPropertyPath(), i.getMessage()))
                .collect(Collectors.joining("\n"));
    }
}
