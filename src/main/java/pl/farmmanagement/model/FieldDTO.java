package pl.farmmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO {

    private Long id;
    @NotNull(message = "required")
    private String name;
    @NotNull(message = "required")
    private double area;
}
