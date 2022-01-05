package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.controller.util.SitePathDistribution;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController implements SitePathDistribution {
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String DefaultSite() {
        return indexSite;
    }
}
