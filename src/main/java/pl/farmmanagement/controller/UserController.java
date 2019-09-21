package pl.farmmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.model.User;
import pl.farmmanagement.security.LoggedUserDetails;
import pl.farmmanagement.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
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

    @GetMapping
    public ModelAndView loginPage(@RequestParam(required = false) String error, HttpServletRequest httpServletRequest) {
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
            return "redirect:/";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userHomePage(Model model, @AuthenticationPrincipal LoggedUserDetails userDetails) {
        List<FieldEntity> allUserField = userService.getAllUserFieldById(userDetails.getId());
        model.addAttribute("fields", allUserField);
        return "userHomePage";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/userUpdate")
    public String userUpdate(Model model, @AuthenticationPrincipal LoggedUserDetails userDetails) {
        User user = userService.findById(userDetails.getId());
        model.addAttribute("user", user);
        return "userPage";
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/user/update")
    public String processUserUpdate( @AuthenticationPrincipal LoggedUserDetails userDetails,@Valid @ModelAttribute("user") User user,
                              BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return "userPage";
        } else {
            userService.add(user);
            return "redirect:/user";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ModelAndView adminPage(){
        List<User> userList = userService.findAllUsers();
        ModelAndView modelAndView = new ModelAndView("adminPage.html");
        modelAndView.addObject("users",userList);
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/delete")
    public String deleteField(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
