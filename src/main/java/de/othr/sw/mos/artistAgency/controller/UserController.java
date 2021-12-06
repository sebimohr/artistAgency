package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.service.userService.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }
}
