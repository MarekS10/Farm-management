package pl.farmmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.farmmanagement.model.FieldOperationEntity;

import java.util.List;

public interface FieldOperationRepository extends JpaRepository<FieldOperationEntity, Long> {

  List<FieldOperationEntity> findAllByFieldEntity_Id(Long id);
}
