package pl.farmmanagement.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.farmmanagement.model.FieldEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FieldRepositoryTest {

    private FieldEntity fieldEntity;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    FieldRepository repository;

    @Before
    public void setUp() {
        fieldEntity = FieldEntity
                .builder()
                .name("Field-1")
                .area(0.8)
                .build();
    }

    @Test
    public void whenAddField_thenShouldSaveField() {

        fieldEntity = testEntityManager.persistAndFlush(fieldEntity);

        assertEquals(fieldEntity, repository.findById(fieldEntity.getId()).get());
    }

    @Test
    public void whenFindFieldById_thenShouldFindField() {

        Long theId = (Long) testEntityManager.persistAndGetId(fieldEntity);
        FieldEntity foundField = testEntityManager.find(FieldEntity.class, theId);

        assertEquals(fieldEntity, foundField);
    }

    @Test
    public void whenDeleteField_thenFieldShouldBeRemoved() {

        Long theId = (Long) testEntityManager.persistAndGetId(fieldEntity);
        testEntityManager.remove(fieldEntity);

        FieldEntity foundField = testEntityManager.find(FieldEntity.class, theId);

        assertNull(foundField);
    }
}