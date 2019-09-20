package pl.farmmanagement.helper;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueFieldNameValidator.class)
public @interface UniqueFieldName {

    String message() default "Field name has already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
