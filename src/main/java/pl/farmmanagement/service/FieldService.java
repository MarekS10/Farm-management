package pl.farmmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.farmmanagement.model.FieldDTO;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.repository.FieldRepository;

@Service
public class FieldService {

    private FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public FieldDTO addField(FieldDTO dto) {
        FieldEntity field = mapToFieldEntity(dto);
        FieldEntity savedField = fieldRepository.save(field);
        return mapToFieldDTO(savedField);
    }


    private FieldEntity mapToFieldEntity(FieldDTO dto) {
        return FieldEntity
                .builder()
                .name(dto.getName())
                .area(dto.getArea())
                .build();
    }

    private FieldDTO mapToFieldDTO(FieldEntity field) {
        return FieldDTO
                .builder()
                .id(field.getId())
                .name(field.getName())
                .area(field.getArea())
                .build();
    }
}
