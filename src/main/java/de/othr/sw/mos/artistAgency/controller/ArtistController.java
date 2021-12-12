package de.othr.sw.mos.artistAgency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/artist")
public class ArtistController {
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String ShowArtistsList() {
        return "artist/artistList";
    }
}
