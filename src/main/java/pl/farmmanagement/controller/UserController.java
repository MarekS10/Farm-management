package pl.farmmanagement.controller;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.farmmanagement.model.User;
import pl.farmmanagement.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  @GetMapping(value = "/singup")
  public String showForm(Model model) {
    model.addAttribute("user", new User());
    return "showForm";
  }

  @GetMapping(value = {"/","/home"})
  public String showHome(Model model) {
    return "home";
  }

  @PostMapping(value = "/singup")
  public String processForm(@Valid @ModelAttribute("user") User user, BindingResult result) {
    if(result.hasErrors()){
      return "showForm";
    }else {
      userService.add(user);
      return "redirect:/home";
    }
  }

  @PostMapping(value = "/login")
  public String processLogin(@Valid @ModelAttribute("user") User user, BindingResult result){
    if(result.hasErrors()){
      return "home";
    }else {
      User byUserName = userService.getByUserName(user.getUserName());
      if(byUserName.getPassword().equals(user.getPassword())){
        return "redirect:/user";
      }else {
        return "home";
      }
    }
  }
}
