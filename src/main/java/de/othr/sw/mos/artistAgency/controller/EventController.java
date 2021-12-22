package de.othr.sw.mos.artistAgency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/event")
public class EventController implements SitePathDistribution {
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String ShowEventList() {
        return eventListSite;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String EventBookingSite() {
        return bookNewEventSite;
    }
}
