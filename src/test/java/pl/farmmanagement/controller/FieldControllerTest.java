package pl.farmmanagement.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.farmmanagement.model.FieldDTO;
import pl.farmmanagement.service.FieldService;

@RunWith(SpringRunner.class)
@WebMvcTest(FieldController.class)
public class FieldControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FieldService fieldService;

    @Test
    public void whenAccessNewFieldEndPoint_thenReturnOkStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/newField"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenCreateFieldWithIncorrectData_thenReturnsStatusConflict() throws Exception {
        FieldDTO field = FieldDTO
                .builder()
                .name("")
                .area(10.1)
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/user/newField").flashAttr("newField",field))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void whenCreateFieldWithCorrectData_thenReturnsStatusFound() throws Exception {
        FieldDTO field = FieldDTO
                .builder()
                .name("Field-1")
                .area(10.1)
                .build();
        mvc.perform(MockMvcRequestBuilders.post("/user/newField").flashAttr("newField",field))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound());
    }


}