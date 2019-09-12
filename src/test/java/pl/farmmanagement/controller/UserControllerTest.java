package pl.farmmanagement.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.model.User;
import pl.farmmanagement.service.UserService;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> argumentCaptor;

    private User user;

    @Before
    public void setUp() {
        user = User
                .builder()
                .id(1L)
                .userName("root12")
                .password("root1234")
                .eMail("root@gmail.com")
                .givenName("Root")
                .surname("Root")
                .build();
    }

    @Test
    public void whenAccessSignUpEndPoint_thenReturnsOkStatus() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/signUp"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenAccessHomeEndPoint_thenReturnsOkStatus() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/home"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenCreateUserWithCorrectData_thenReturnsFoundStatusAndUserDetailsAreCorrect() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/signUp")
                .flashAttr("user", user))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound());

        Mockito.verify(userService, Mockito.times(1))
                .add(argumentCaptor.capture());
        assertEquals("root12", argumentCaptor.getValue().getUserName());
        assertEquals("root1234", argumentCaptor.getValue().getPassword());
        assertEquals("root@gmail.com", argumentCaptor.getValue().getEMail());
        assertEquals("Root", argumentCaptor.getValue().getGivenName());
        assertEquals("Root", argumentCaptor.getValue().getSurname());
    }

    @Test
    public void whenCreateUserWithIncorrectDta_thenReturnsConflictStatus() throws Exception {
        String invalidName = "";
        user.setUserName(invalidName);

        mvc.perform(MockMvcRequestBuilders.post("/signUp")
                .flashAttr("user", user))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict());

        Mockito.verify(userService, Mockito.times(0)).add(user);
    }

    @Test
    public void whenLoggedInSuccessful_thenReturnsFoundStatusAndUserView() throws Exception {
        String validName = "root12";
        String validPassword = "root1234";

        Mockito.when(userService.getByUserNameAndPassword(validName, validPassword))
                .thenReturn(Optional.of(user));
        mvc.perform(MockMvcRequestBuilders.post("/user")
                .flashAttr("user", user))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/user"));
    }

    @Test
    public void whenLoggedInFailed_thenReturnsFoundStatusAndHomeView() throws Exception {
        String invalidName = "root";
        String validPassword = "root1234";

        Mockito.when(userService.getByUserNameAndPassword(invalidName, validPassword))
                .thenReturn(Optional.of(user));

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .flashAttr("user", user))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/home"));
    }

    @Test
    public void whenLoggedInSuccessful_thenReturnsOkStatusAndAllUserField() throws Exception {

        FieldEntity field1 = FieldEntity
                .builder()
                .id(1L)
                .name("Field-1")
                .area(0.8)
                .build();

        Mockito.when(userService.getAllUserFieldById(1L))
                .thenReturn(Collections.singletonList(field1));

        mvc.perform(MockMvcRequestBuilders.get("/user")
                .sessionAttr("userId", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("fields", Collections.singletonList(field1)));
    }
}