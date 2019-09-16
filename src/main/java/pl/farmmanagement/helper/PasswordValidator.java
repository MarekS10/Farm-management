package pl.farmmanagement.helper;

import pl.farmmanagement.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, User> {


    @Override
    public void initialize(PasswordValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getRePassword());
    }
}
