package pl.farmmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.model.FieldOperation;
import pl.farmmanagement.model.FieldOperationEntity;
import pl.farmmanagement.service.FieldOperationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/operations")
public class FieldOperationController {

  private final FieldOperationService fieldOperationService;

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  @GetMapping
  public String operations(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
    request.getSession().setAttribute("fieldId", id);
    List<FieldOperationEntity> allFieldOperations =
        fieldOperationService.findAllOperationsByField(id);
    model.addAttribute("operations", allFieldOperations);
    return "operations";
  }

  @GetMapping(value = "/new")
  public String newOperation(Model model) {
    model.addAttribute("newOperation", new FieldOperation());
    return "newOperation-form";
  }

  @PostMapping(value = "/new")
  public String processForm(
      @Valid @ModelAttribute("newOperation") FieldOperation newOperation,
      BindingResult result,
      HttpServletRequest request,
      HttpServletResponse response) {
    if (result.hasErrors()) {
      response.setStatus(HttpServletResponse.SC_CONFLICT);
      return "newOperation-form";
    } else {
      Long fieldId = (Long) request.getSession().getAttribute("fieldId");
      FieldEntity field = fieldOperationService.findFieldById(fieldId);
      newOperation.setFieldEntity(field);
      fieldOperationService.addFieldOperation(newOperation);

      Long id = field.getId();
      return "redirect:/user/operations?id=" + id;
    }
  }
}
