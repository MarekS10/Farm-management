package pl.farmmanagement.helper;

import pl.farmmanagement.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, User> {


    @Override
    public void initialize(PasswordValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        String thePassword = user.getPassword();
        if (thePassword != null) {
            return user.getPassword().equals(user.getRePassword());
        } else {
            return false;
        }
    }
}
