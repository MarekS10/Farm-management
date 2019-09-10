package pl.farmmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.farmmanagement.model.FieldDTO;
import pl.farmmanagement.model.UserEntity;
import pl.farmmanagement.service.FieldService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/newField")
    public String showFormForAddField(Model theModel) {
        FieldDTO newField = new FieldDTO();
        theModel.addAttribute("newField", newField);
        return "newField-form";
    }

    @PostMapping("/newField")
    public String saveField(@ModelAttribute("newField") @Valid FieldDTO field,
                            BindingResult bindingResult,
                            HttpServletResponse response,
                            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return "newField-form";
        } else {
            Long userId = (Long) request.getSession().getAttribute("userId");
            UserEntity fieldOwner = fieldService.findFieldOwner(userId);
            field.setUserEntity(fieldOwner);
            fieldService.addField(field);
            return "redirect:/user";
        }
    }

    @GetMapping("/delete")
    public String deleteField(@RequestParam("id") Long id){
        fieldService.deleteField(id);
        return "redirect:/user";
    }

}
