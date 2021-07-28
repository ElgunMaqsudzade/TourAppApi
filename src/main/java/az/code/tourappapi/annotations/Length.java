package az.code.tourappapi.annotations;

import az.code.tourappapi.annotations.validators.LengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LengthValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
    String message() default "Field value doesn't match to the pattern";

    int max() default Short.MAX_VALUE;

    int min() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
