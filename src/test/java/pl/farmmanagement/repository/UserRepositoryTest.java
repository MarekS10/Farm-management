package pl.farmmanagement.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.model.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    private UserEntity userEntity;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository repository;

    @Before
    public void setUp() {
        userEntity = UserEntity
                .builder()
                .userName("Root12")
                .password("Root1234")
                .eMail("root@gmail.com")
                .build();
    }

    @Test
    public void whenAddUser_thenUserShouldBeAdded() {

        Long theId = (Long) testEntityManager.persistAndGetId(userEntity);

        assertEquals(userEntity, repository.findById(theId).get());
    }

    @Test
    public void whenFindByUserName_thenShouldReturnsFoundUser() {

        testEntityManager.persistAndFlush(userEntity);

        UserEntity foundUser = repository.findByUserNameIgnoreCase("Root12");

        assertEquals(userEntity, foundUser);

    }

    @Test
    public void whenFindByUserNameAndPass_thenShouldReturnsFoundUser() {
        String userName = "root12";
        String userPass = "Root1234";

        testEntityManager.persistAndFlush(userEntity);

        Optional<UserEntity> foundUser = repository.findByUserNameIgnoreCaseAndPassword(userName, userPass);

        assertEquals(userEntity, foundUser.get());
    }

    @Test
    public void whenFindUserFieldsById_thenShouldReturnsUserFields() {
        FieldEntity field1 = FieldEntity
                .builder()
                .name("Field-1")
                .area(0.8)
                .user(userEntity)
                .build();

        FieldEntity field2 = FieldEntity
                .builder()
                .name("Field-2")
                .area(3.0)
                .user(userEntity)
                .build();

        List<FieldEntity> userFields = Arrays.asList(field1, field2);

        userEntity.setUserFields(userFields);

        Long userId = (Long) testEntityManager.persistAndGetId(userEntity);

        assertEquals(userFields, repository.userFieldsById(userId));
    }
}