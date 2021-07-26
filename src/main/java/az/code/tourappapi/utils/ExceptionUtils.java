package az.code.tourappapi.utils;

import az.code.tourappapi.models.dtos.ExceptionDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class ExceptionUtils {
    public static List<ExceptionDTO> getFailedResponse(ConstraintViolationException ex) {
        List<ExceptionDTO> fErrors = ex.getConstraintViolations().stream()
                .parallel().map(i -> ExceptionDTO.builder()
                        .fieldName(i.getPropertyPath().toString())
                        .errorMessage(i.getMessage())
                        .build())
                .collect(Collectors.toList());

       return fErrors;
    }

    public static List<ExceptionDTO> getFailedResponse(MethodArgumentNotValidException ex) {
        List<ExceptionDTO> global = ex.getGlobalErrors().stream()
                .parallel().map(i -> ExceptionDTO.builder()
                        .fieldName(i.getObjectName())
                        .errorMessage(i.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        List<ExceptionDTO> field = ex.getBindingResult().getFieldErrors().stream()
                .parallel().map(i -> ExceptionDTO.builder()
                        .fieldName(i.getField())
                        .errorMessage(i.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        global.addAll(field);

        return global;
    }
}
