package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.controller.util.SitePathDistribution;
import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.EventServiceException;
import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Controller
@RequestMapping(value = "/event")
public class EventController implements SitePathDistribution {

    @Autowired
    private EventBookingServiceIF eventService;

    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = {"/list", "/", ""}, method = RequestMethod.GET)
    public String ShowDefaultEventList(
            Model model
    ) {
        var eventList = eventService.getAllEvents();

        model.addAttribute("events", eventList);

        return eventListSite;
    }

    @RequestMapping(value = "/myEvents", method = RequestMethod.GET)
    public String MyEventSite(
            Model model,
            Principal principal
    ) {
        var artistEventList = eventService.getAllEventsForSpecificArtist(getCurrentlyLoggedInUser(principal).getID());

        model.addAttribute("events", artistEventList);

        return eventListSite;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String EventBookingSite(
            Model model,
            @RequestParam(required = false) String venueLocation,
            @RequestParam(required = false) String venueDate,
            @RequestParam(required = false) Integer venueCapacity
    ) {
        if(validateVenueSearchInput(venueLocation, venueDate, venueCapacity)) {
            // TODO: RPC on EventLocationManager
            var venueList = eventService.getAllVenuesFromEventLocationManager();
            model.addAttribute("venues", venueList);

            model.addAttribute("venueLocation", venueLocation);
            model.addAttribute("venueDate", venueDate);
            model.addAttribute("venueCapacity", venueCapacity);

            var eventDate = parseDateFromHTMLToDateObject(venueDate);

            var newEvent = new Event();
            newEvent.setEventDate(eventDate);
            model.addAttribute("event", newEvent);
        } else {
            model.addAttribute("venueLocation", "AUGSBURG");
            model.addAttribute("venueDate", "2022-01-21");
            model.addAttribute("venueCapacity", 100);
        }

        return bookNewEventSite;
    }

    @RequestMapping(value="/book", method = RequestMethod.POST)
    public String BookEvent(
            Model model,
            Principal principal,
            @ModelAttribute("event") Event event

    ) {
        if(event.getVenueId() == null) {
            model.addAttribute("event", event);
            return bookNewEventSite;
        } else {
            try {
                event.setArtist(getCurrentlyLoggedInUser(principal));
                eventService.registerEvent(event);
                return MyEventSite(model, principal);
            } catch (EventServiceException e) {
                model.addAttribute("errorMessage", e.getMessage());
                return errorSite;
            }
        }
    }

    private User getCurrentlyLoggedInUser(Principal principal) {
        return userService.loadUserByUsername(principal.getName());
    }

    private Date parseDateFromHTMLToDateObject(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd")
                    .parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Boolean validateVenueSearchInput(String venueLocation, String venueDate, Integer venueCapacity) {
        return venueLocation != null
                && Arrays.asList("AUGSBURG", "INGOLSTADT", "MUNICH", "NUREMBURG", "REGENSBURG").contains(venueLocation)
                && venueDate != null
                && venueCapacity != null
                && Arrays.asList(100, 500, 1000, 5000, 100000).contains(venueCapacity);
    }
}
