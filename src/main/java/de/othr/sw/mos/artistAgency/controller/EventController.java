package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping(value = "/event")
public class EventController implements SitePathDistribution {

    @Autowired
    private EventBookingServiceIF eventService;

    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = {"/list", "/", ""}, method = RequestMethod.GET)
    public String ShowDefaultEventList(Model model) {
        var eventList = eventService.getAllEvents();

        model.addAttribute("events", eventList);

        return eventListSite;
    }

    @RequestMapping(value = "/myEvents", method = RequestMethod.GET)
    public String MyEventSite(Model model, Principal principal) {
        var artistEventList = eventService.getAllEventsForSpecificArtist(getCurrentlyLoggedInUserId(principal));

        model.addAttribute("events", artistEventList);

        return eventListSite;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String EventBookingSite() {
        return bookNewEventSite;
    }

    private Long getCurrentlyLoggedInUserId(Principal principal) {
        return userService.loadUserByUsername(principal.getName()).getUserId();
    }
}
