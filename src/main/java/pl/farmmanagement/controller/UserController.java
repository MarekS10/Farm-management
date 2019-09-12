package pl.farmmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.model.User;
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
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "newUser-form";

    }


    @GetMapping(value = {"/", "/home"})
    public String showHome(Model model) {
        model.addAttribute("user",new User());
        return "home";
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

    @PostMapping(value = "/user")
    public String processLogin(@ModelAttribute("user") User user, HttpServletRequest request) {
        String userName = user.getUserName();
        String userPassword = user.getPassword();

        Optional<User> logInUser = userService.getByUserNameAndPassword(userName, userPassword);
        if (logInUser.isPresent()){
            request.getSession().setAttribute("userId",logInUser.get().getId());
            request.getSession().setAttribute("userName",logInUser.get().getUserName());
            return "redirect:/user";
        }else
            request.getSession().setAttribute("logError","logError");
            return "redirect:/home";
        }

    @GetMapping("/user")
    public String userHomePage(HttpServletRequest request, Model model){
        Long userId = (Long) request.getSession().getAttribute("userId");
        List<FieldEntity> allUserField = userService.getAllUserFieldById(userId);
        model.addAttribute("fields",allUserField);
        return "userHomePage";
    }
}
