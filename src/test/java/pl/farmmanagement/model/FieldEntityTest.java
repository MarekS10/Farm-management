package pl.farmmanagement.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldEntityTest {

    private FieldEntity field;

    @Before
    public void setUp() throws Exception {
        field = FieldEntity
                .builder()
                .id(1L)
                .name("Field-1")
                .area(0.8)
                .build();
    }

    @Test
    public void whenFieldBuilt_thenFieldIdIsCorrect(){
        Long expectedId=1L;
        assertEquals(expectedId,field.getId());
    }

    @Test
    public void whenFieldBuilt_thenFieldNameIsCorrect(){
        String expectedName = "Field-1";
        assertEquals(expectedName,field.getName());
    }

    @Test
    public void whenFieldBuilt_thenFieldAreaIsCorrect(){
        double expectedArea = 0.8;
        assertEquals(expectedArea,field.getArea(),0.01);
    }
}