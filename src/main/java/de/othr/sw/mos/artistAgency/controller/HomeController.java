package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.controller.util.ControllerTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController extends ControllerTemplate {
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String DefaultSite() {
        return indexSite;
    }
}
