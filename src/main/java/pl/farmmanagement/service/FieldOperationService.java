package pl.farmmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.model.FieldOperation;
import pl.farmmanagement.model.FieldOperationEntity;
import pl.farmmanagement.repository.FieldOperationRepository;
import pl.farmmanagement.repository.FieldRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldOperationService {

  private final FieldOperationRepository fieldOperationRepository;
  private final FieldRepository fieldRepository;

  public FieldOperation addFieldOperation(FieldOperation fieldOperation) {
    FieldOperationEntity fieldOperationEntity = mapToFieldOperationEntity(fieldOperation);
    fieldOperationRepository.save(fieldOperationEntity);
    return maptoFieldOperation(fieldOperationEntity);
  }

  public List<FieldOperationEntity> findAllOperationsByField(Long id) {
    return fieldOperationRepository.findAllByFieldEntity_Id(id);
  }

  public FieldEntity findFieldById(Long id) {
    return fieldRepository.findById(id).get();
  }

  private FieldOperation maptoFieldOperation(FieldOperationEntity field) {
    return FieldOperation.builder()
        .id(field.getId())
        .task(field.getTask())
        .operationDate(field.getOperationDate())
        .isDone(field.isDone())
        .fieldEntity(field.getFieldEntity())
        .build();
  }

  private FieldOperationEntity mapToFieldOperationEntity(FieldOperation field) {
    return FieldOperationEntity.builder()
        .id(field.getId())
        .task(field.getTask())
        .operationDate(field.getOperationDate())
        .isDone(field.isDone())
        .fieldEntity(field.getFieldEntity())
        .build();
  }
}
