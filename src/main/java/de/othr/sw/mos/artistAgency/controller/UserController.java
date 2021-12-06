package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.service.userService.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = "/registerUser", method = RequestMethod.GET)
    public String index(Model model) {

        var newUser = new User();

        model.addAttribute("user", newUser);

        return "registerUser";
    }

    @RequestMapping(value="/registerUser", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.registerUser(user);
        }
        catch (UserServiceException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "registerUser";
        }
        model.addAttribute("success", "successfull registration");
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }
}
