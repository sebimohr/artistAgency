package de.othr.sw.mos.artistAgency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static de.othr.sw.mos.artistAgency.controller.SitePathDistribution.*;

@Controller
public class HomeController {
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String DefaultSite() {
        return indexSite;
    }
}
