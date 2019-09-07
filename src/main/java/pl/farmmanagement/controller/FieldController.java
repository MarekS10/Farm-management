package pl.farmmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.farmmanagement.model.FieldDTO;
import pl.farmmanagement.service.FieldService;

@Controller
@RequestMapping("/user")
public class FieldController {

    private FieldService fieldService;

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/newField")
    public String showFormForAddField(Model theModel){
        FieldDTO newField = new FieldDTO();
        theModel.addAttribute("newField",newField);
        return "newField-form";
    }
}
