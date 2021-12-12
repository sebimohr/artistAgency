package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String RegisterUserSite(Model model) {

        var newUser = new User();

        model.addAttribute("user", newUser);

        return "user/registerUser";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String AddUserToDatabase(@ModelAttribute("user") User user, Model model) {
        try {
            userService.registerUser(user);
        }
        catch (UserServiceException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/registerUser";
        }
        model.addAttribute("success", "successfull registration");
        return "user/login";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam(required = false) String loginCode) {
        // TODO: handle login codes
        /*if(loginCode == "success")
            return "default/index";
        else if (loginCode == "error")
            return "user/login";
        else if (loginCode == "logout")
            return "default/index";
        else*/
            return "user/login";
    }

    @RequestMapping(value = "/myProfile", method = RequestMethod.GET)
    public String ShowMyProfile() {
        return "user/myProfile";
    }

    @RequestMapping(value = "/myProfile/edit", method = RequestMethod.GET)
    public String EditMyProfileSite() {
        return "user/editMyProfile";
    }
}
