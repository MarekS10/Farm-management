package pl.farmmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.farmmanagement.helper.UniqueFieldName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO {

    private Long id;

    @NotNull(message = "Required")
    @Size(min = 1,message = "Required")
    @UniqueFieldName
    private String name;

    @Positive(message = "Must be higher that 0")
    private double area;

    private List<FieldOperationEntity> operationsList;

    private UserEntity userEntity;
}
