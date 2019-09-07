package pl.farmmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO {

    private Long id;

    @NotNull(message = "required")
    @Size(min = 1, message = "required")
    private String name;

    @Positive(message = "Must be higher that 0")
    private double area;
}
