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
public class UserController implements SitePathDistribution {
    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String RegisterUserSite(Model model) {

        var newUser = new User();

        model.addAttribute("user", newUser);

        return registerUserSite;
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String AddUserToDatabase(@ModelAttribute("user") User user, Model model) {
        try {
            userService.registerUser(user);
        }
        catch (UserServiceException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return registerUserSite;
        }
        model.addAttribute("success", "Erfolgreich registriert. Hallo " + user.getUsername() + ".");
        return loginSite;
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "code", required = false) String loginCode, Model model) {
        // TODO: handle login codes
        if(loginCode != null) {
            switch (loginCode) {
                case "success" -> {
                    model.addAttribute("loginSuccess", "Erfolgreich angemeldet, willkommen zurück!");
                    return myProfileSite;
                }
                case "logout" -> {
                    model.addAttribute("logoutSuccess", "Erfolgreich abgemeldet, auf Wiedersehen!");
                    return indexSite;
                }
                case "error" -> {
                    model.addAttribute("errorMessage", "Benutzername oder Passwort sind falsch!");
                    return loginSite;
                }
                default -> {
                    model.addAttribute("errorMessage", "Falscher loginCode, bitte wenden Sie sich an den Administrator!");
                    return errorSite;
                }
            }
        }
        return loginSite;
    }

    @RequestMapping(value = "/myProfile", method = RequestMethod.GET)
    public String ShowMyProfile() {
        return myProfileSite;
    }

    @RequestMapping(value = "/myProfile/edit", method = RequestMethod.GET)
    public String EditMyProfileSite() {
        return editMyProfileSite;
    }
}
