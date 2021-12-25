package de.othr.sw.mos.artistAgency.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandlerController implements ErrorController, SitePathDistribution {

    @RequestMapping("/error")
    public String getErrorPath(Model model) {
        model.addAttribute("errorMessage", "Keine Fehlermeldung vorhanden. Gehen Sie zurück zur Homepage.");
        return errorSite;
    }
}
