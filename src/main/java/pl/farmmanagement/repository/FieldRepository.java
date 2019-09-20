package pl.farmmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.farmmanagement.model.FieldEntity;

public interface FieldRepository extends JpaRepository<FieldEntity,Long> {

    FieldEntity findByNameIgnoreCase(String fieldName);
}
