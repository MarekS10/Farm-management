package pl.farmmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.model.FieldOperation;
import pl.farmmanagement.model.FieldOperationEntity;
import pl.farmmanagement.repository.FieldOperationRepository;
import pl.farmmanagement.repository.FieldRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldOperationService {

    private final FieldOperationRepository fieldOperationRepository;
    private final FieldRepository fieldRepository;

    public FieldOperation addFieldOperation(Long fieldId, FieldOperation fieldOperation) {

        fieldRepository.findById(fieldId)
                .ifPresent(fieldOperation::setFieldEntity);
        FieldOperationEntity fieldOperationEntity = mapToFieldOperationEntity(fieldOperation);
        fieldOperationRepository.save(fieldOperationEntity);
        return mapToFieldOperation(fieldOperationEntity);
    }

    public List<FieldOperationEntity> findAllOperationsByField(Long id) {
        return fieldOperationRepository.findAllByFieldEntityId(id);
    }

    public Optional<FieldOperation> findOperationById(Long id) {
        Optional<FieldOperationEntity> foundFieldOperation = fieldOperationRepository.findById(id);
        if (foundFieldOperation.isPresent()) {
            FieldOperation fieldOperation = mapToFieldOperation(foundFieldOperation.get());
            return Optional.of(fieldOperation);
        } else {
            return Optional.empty();
        }
    }

    public FieldOperation doneTask(Long fieldId, Long id) {
        Optional<FieldOperationEntity> foundOperation = fieldOperationRepository.findById(id);
        foundOperation.ifPresent(operation -> {
            operation.setDone(true);
            FieldOperation fieldOperation = mapToFieldOperation(operation);
            addFieldOperation(fieldId,fieldOperation);
        });
        return mapToFieldOperation(foundOperation.get());
    }

    public void deleteById(Long id) {
        fieldOperationRepository.deleteById(id);
    }

    private FieldOperation mapToFieldOperation(FieldOperationEntity field) {
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
