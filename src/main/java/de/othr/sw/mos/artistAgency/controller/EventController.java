package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.entity.Event;
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
    public String EventBookingSite(Model model,
                                   @RequestParam(required = false) String venueLocation,
                                   @RequestParam(required = false) String venueDate,
                                   @RequestParam(required = false) Integer venueCapacity,
                                   @ModelAttribute String eventName
    ) {
        var newEvent = new Event();

        // TODO: don't delete eventName when reloading the page -> how to get value from eventName field?
        if(!eventName.isBlank())
            newEvent.setEventName(eventName);
        else
            newEvent.setEventName("TestEventName For HTML");        // TODO: just for testing, delete afterwards

        if(venueLocation != null && venueDate != null && venueCapacity != null) {
            if(Arrays.asList(100, 500, 1000, 5000, 100000).contains(venueCapacity)){
                // TODO: RPC on EventLocationManager
                var venueList = eventService.getAllVenuesFromEventLocationManager();
                model.addAttribute("venues", venueList);

                model.addAttribute("venueLocation", venueLocation);
                model.addAttribute("venueDate", venueDate);
                model.addAttribute("venueCapacity", venueCapacity);

                Date eventDate = null;
                try {
                    eventDate = new SimpleDateFormat("yyyy-MM-dd")
                                    .parse(venueDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                newEvent.setEventDate(eventDate);
            }
        } else {
            model.addAttribute("venueLocation", "AUGSBURG");
            model.addAttribute("venueDate", "2022-01-21");
            model.addAttribute("venueCapacity", 100);
        }

        model.addAttribute("event", newEvent);

        return bookNewEventSite;
    }

    @RequestMapping(value="/book", method = RequestMethod.POST)
    public String BookEvent(@ModelAttribute("event") Event event, Model model) {
        if(event.getVenueId() == null) {
            model.addAttribute("event", event);
            return bookNewEventSite;
        } else {
            try{
                eventService.registerEvent(event);
            } catch (EventServiceException e) {
                model.addAttribute("errorMessage", e.getMessage());
                return bookNewEventSite;
            }
            return eventListSite;
        }
    }

    private Long getCurrentlyLoggedInUserId(Principal principal) {
        return userService.loadUserByUsername(principal.getName()).getUserId();
    }
}
