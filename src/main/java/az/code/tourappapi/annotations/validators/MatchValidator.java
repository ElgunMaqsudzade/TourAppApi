package az.code.tourappapi.annotations.validators;

import az.code.tourappapi.annotations.Matches;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MatchValidator implements ConstraintValidator<Matches, Object> {
    private String field;
    private String fieldMatch;

    @Override
    public void initialize(Matches cta) {
        this.field = cta.field();
        this.fieldMatch = cta.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        if (fieldValue != null && fieldMatchValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return false;
        }
    }

}