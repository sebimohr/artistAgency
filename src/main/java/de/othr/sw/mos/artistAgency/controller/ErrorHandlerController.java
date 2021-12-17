package de.othr.sw.mos.artistAgency.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static de.othr.sw.mos.artistAgency.controller.SitePathDistribution.*;

@Controller
public class ErrorHandlerController implements ErrorController {

    @RequestMapping("/error")
    public String getErrorPath() {
        return errorSite;
    }
}
