package carwash.dibo.controller;

import carwash.dibo.validator.UserValidator;
import carwash.dibo.model.User;
import carwash.dibo.service.SecurityService;
import carwash.dibo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class UserController {
    private final SecurityService securityService;
    private final UserValidator userValidator;
    private final UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(userValidator);
    }

    @GetMapping("/")
    public String homePage(){
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login (Model model, String error, String logout){
       if (securityService.isAuthenticated()){
           return "dashboard";
       }
       if (error != null){
           model.addAttribute("error", "You username and password is invalid.");
       }
       if (logout != null){
           model.addAttribute("message", "You have been logged out successfully");
       }
       return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        model.addAttribute("message", "You have been logged out successfully");

        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        if (securityService.isAuthenticated()){
            return "redirect:/dashboard";
        }

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Validated @ModelAttribute("userForm") User userForm, BindingResult bindingResult){
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()){
            return "registration";
        }

        userService.saveUser(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }
}
