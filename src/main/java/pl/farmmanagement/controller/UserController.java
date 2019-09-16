package pl.farmmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.model.User;
import pl.farmmanagement.security.SecurityConfig;
import pl.farmmanagement.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping(value = "/signUp")
    public ModelAndView showForm(Model model) {
        ModelAndView modelAndView = new ModelAndView("newUser-form.html");
        model.addAttribute("user", new User());
        return modelAndView;

    }
    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("loginPage.html");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @PostMapping(value = "/signUp")
    public String processForm(@Valid @ModelAttribute("user") User user,
                              BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return "newUser-form";
        } else {
            userService.add(user);
            return "redirect:/home";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userHomePage(HttpServletRequest request, Model model){
        Long userId = (Long) request.getSession().getAttribute("userId");
        List<FieldEntity> allUserField = userService.getAllUserFieldById(userId);
        model.addAttribute("fields",allUserField);
        return "userHomePage";
    }
}
