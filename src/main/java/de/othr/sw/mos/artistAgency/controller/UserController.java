package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.controller.util.ControllerTemplate;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController extends ControllerTemplate {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String RegisterUserSite(
            Model model
    ) {

        var newUser = new User();

        model.addAttribute("user", newUser);

        return registerUserSite;
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String AddUserToDatabase(
            Model model,
            @ModelAttribute("user") User user
    ) {
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

    @RequestMapping(value = {"/login", "/", ""})
    public String login(
            Model model,
            Principal principal,
            @RequestParam(value = "code", required = false) String loginCode
    ) {
        if(loginCode != null) {
            switch (loginCode) {
                case "success" -> {
                    if(principal != null) {
                        User currentUser;
                        try {
                            currentUser = getCurrentlyLoggedInUser(principal);
                        } catch (UserServiceException e) {
                            return renderErrorPageOnException(model, e.getMessage());
                        }
                        model.addAttribute("loginSuccess", "Erfolgreich angemeldet, willkommen zurück " + currentUser.getArtistName() + "!");
                        model.addAttribute("currentUser", currentUser);
                        return myProfileSite;
                    }
                }
                case "error" -> {
                    model.addAttribute("errorMessage", "Benutzername oder Passwort sind falsch!");
                    return loginSite;
                }
                case "logout" -> {
                    if(principal != null) {
                        model.addAttribute("errorMessage", "Nutzen Sie zum Logout bitte den zugehörigen Button!");
                    } else {
                        model.addAttribute("logoutSuccess", "Erfolgreich abgemeldet, auf Wiedersehen!");
                    }
                    return indexSite;
                }
                default -> {
                    model.addAttribute("errorMessage", "Falscher loginCode. Falls dieses Problem länger besteht, wenden Sie sich bitte an den Administrator.");
                    return loginSite;
                }
                // TODO: ONLY FOR DEVELOPMENT, REMOVE BEFORE DEPLOYING
                case "defaultLogin" -> {
                    if(principal == null){
                        return loginDefaultUserSite;
                    }
                    model.addAttribute("errorMessage", "Du bist schon angemeldet Dumbo.");
                    return indexSite;
                }
            }
            model.addAttribute("errorMessage", "Keine Berechtigung zum aufrufen dieser Seite, bitte melden Sie sich zuerst an!");
        }
        return loginSite;
    }
}
