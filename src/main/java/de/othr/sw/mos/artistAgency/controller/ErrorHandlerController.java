package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.controller.util.ControllerTemplate;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandlerController extends ControllerTemplate implements ErrorController {

    @RequestMapping("/error")
    public String getErrorPath(
            Model model
    ) {
        return renderErrorPageOnException(model, "Keine Fehlermeldung vorhanden. Gehen Sie zurück zur Homepage.");
    }
}
