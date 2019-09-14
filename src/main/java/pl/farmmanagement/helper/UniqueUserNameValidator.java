package pl.farmmanagement.helper;

import lombok.RequiredArgsConstructor;
import pl.farmmanagement.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {


    private final UserRepository userRepository;

    public void initialize(UniqueUserName constraint) {
    }

    public boolean isValid(String userName, ConstraintValidatorContext context) {
        return userRepository.findByUserName(userName) == null;
    }

}
