package pl.farmmanagement.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class FieldOperationEntityTest {

    FieldOperationEntity fieldOperation;

    @Before
    public void setUp() throws Exception {
        fieldOperation = FieldOperationEntity
                .builder()
                .id(1L)
                .operationDate(LocalDate.of(2019, 9, 7))
                .isDone(true)
                .build();
    }

    @Test
    public void whenFieldOpBuilt_thenFieldOpDataIsCorrect() {
        Long expectedId = 1L;
        LocalDate expectedDate = LocalDate.of(2019, 9, 7);
        boolean expectedDone = true;

        assertEquals(expectedId, fieldOperation.getId());
        assertEquals(expectedDate, fieldOperation.getOperationDate());
        assertEquals(expectedDone, fieldOperation.isDone());
    }
}