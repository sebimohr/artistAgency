package de.othr.sw.mos.artistAgency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static de.othr.sw.mos.artistAgency.controller.SitePathDistribution.*;

@Controller
@RequestMapping(value = "/artist")
public class ArtistController {
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String ShowArtistsList() {
        return artistListSite;
    }
}
