package pl.farmmanagement.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FieldTest {

    private static final double MIN_FIELD_AREA = 0.01;
    private FieldEntity field;
    private FieldDTO fieldDTO;

    @Before
    public void setUp() throws Exception {
        FieldOperationEntity tillage = FieldOperationEntity
                .builder()
                .id(1L)
                .operationDate(LocalDate.of(2018,9,20))
                .task("Tillage")
                .isDone(true)
                .build();

        FieldOperationEntity sowing = FieldOperationEntity
                .builder()
                .id(1L)
                .operationDate(LocalDate.of(2018,9,27))
                .task("Sowing")
                .isDone(true)
                .build();

        field = FieldEntity
                .builder()
                .id(1L)
                .name("Field-1")
                .area(2.8)
                .operationsList(Arrays.asList(tillage,sowing))
                .build();

        fieldDTO = FieldDTO
                .builder()
                .id(10L)
                .name("Field-10")
                .area(5.1)
                .operationsList(Arrays.asList(tillage,sowing))
                .build();
    }

    @Test
    public void whenCreatedField_thenFieldDataIsCorrect(){
        String extendedName = "Field-1";
        double extendedArea = 2.8;
        int extendedNumberOfOperations = 2;
        assertEquals(extendedName, field.getName());
        assertEquals(extendedArea, field.getArea(), MIN_FIELD_AREA);
        assertEquals(extendedNumberOfOperations, field.getOperationsList().size());
    }

    @Test
    public void whenCreatedFieldDTO_thenFieldDTODataIsCorrect(){
        String extendedName = "Field-10";
        double extendedArea = 5.1;
        int extendedNumberOfOperations=2;
        assertEquals(extendedName,fieldDTO.getName());
        assertEquals(extendedArea,fieldDTO.getArea(),MIN_FIELD_AREA);
        assertEquals(extendedNumberOfOperations,fieldDTO.getOperationsList().size());
    }
}