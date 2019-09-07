package pl.farmmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.farmmanagement.model.FieldDTO;
import pl.farmmanagement.service.FieldService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @GetMapping("/newField")
    public String showFormForAddField(Model theModel){
        FieldDTO newField = new FieldDTO();
        theModel.addAttribute("newField",newField);
        return "newField-form";
    }

    @PostMapping("/newField")
    public String saveField(@ModelAttribute("newField") @Valid FieldDTO field,
                            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "newField-form";
        }else {
            fieldService.addField(field);
            return "redirect:/user";
        }
    }

}
