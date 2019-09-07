package pl.farmmanagement.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserEntityTest {

  private UserEntity userEntity;

  @Before
  public void setUp() throws Exception {
    userEntity =
        UserEntity.builder()
            .id(1L)
            .userName("user")
            .password("password")
            .eMail("email@email.com")
            .givenName("Jan")
            .surname("Kowalski")
            .build();
  }

  @Test
  public void whenUserBuilt_thenFieldIsCorrect() {
    assertEquals(Long.valueOf(1L), userEntity.getId());
    assertEquals("user", userEntity.getUserName());
    assertEquals("password", userEntity.getPassword());
    assertEquals("email@email.com", userEntity.getEMail());
    assertEquals("Jan", userEntity.getGivenName());
    assertEquals("Kowalski", userEntity.getSurname());
  }
}
