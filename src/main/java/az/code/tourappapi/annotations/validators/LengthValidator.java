package az.code.tourappapi.annotations.validators;

import az.code.tourappapi.annotations.Length;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.invoke.MethodHandles;

public class LengthValidator implements ConstraintValidator<Length, Number> {

    private static final Log LOG = LoggerFactory.make( MethodHandles.lookup() );

    private int min;
    private int max;

    @Override
    public void initialize(Length parameters) {
        min = parameters.min();
        max = parameters.max();
        validateParameters();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        if ( value == null ) {
            return true;
        }
        int length = String.valueOf(value.longValue()).length();
        return length >= min && length <= max;
    }

    private void validateParameters() {
        if ( min < 0 ) {
            throw LOG.getMinCannotBeNegativeException();
        }
        if ( max < 0 ) {
            throw LOG.getMaxCannotBeNegativeException();
        }
        if ( max < min ) {
            throw LOG.getLengthCannotBeNegativeException();
        }
    }
}