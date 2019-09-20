package pl.farmmanagement.helper;

import lombok.RequiredArgsConstructor;
import pl.farmmanagement.repository.FieldRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueFieldNameValidator implements ConstraintValidator<UniqueFieldName, String> {

   private final FieldRepository fieldRepository;

   public void initialize(UniqueFieldName constraint) {
   }

   public boolean isValid(String fieldName, ConstraintValidatorContext context) {
      return fieldRepository.findByNameIgnoreCase(fieldName) == null;
   }
}
