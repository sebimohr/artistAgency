package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.controller.util.ControllerTemplate;
import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping(value = "/event")
public class EventController extends ControllerTemplate {

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
        User currentUser;
        try {
            currentUser = getCurrentlyLoggedInUser(principal);
        } catch (UserServiceException e) {
            return renderErrorPageOnException(model, e.getMessage());
        }

        var artistEventList = eventService.getAllEventsForSpecificArtist(currentUser.getID());

        model.addAttribute("events", artistEventList);

        return eventListSite;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String EventBookingSite(
            Model model,
            @RequestParam(required = false) String venueLocation,
            @RequestParam(required = false) String venueDate,
            @RequestParam(required = false) String venueCapacity
    ) {
        if(venueLocation != null && venueDate != null && venueCapacity != null) {
            venueLocation = venueLocation.toUpperCase();
            var errorMessage = validateVenueSearchInput(venueLocation, venueDate, venueCapacity);

            if(errorMessage == null) {
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
                return bookNewEventSite;
            } else {
                model.addAttribute("errorMessage", errorMessage + ", bitte Suche erneut ausführen.");
            }
        }

        model.addAttribute("venueLocation", "AUGSBURG");
        model.addAttribute("venueDate", "2022-01-21");
        model.addAttribute("venueCapacity", "100");


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
            } catch (Exception e) {
                return renderErrorPageOnException(model, e.getMessage());
            }
        }
    }

    private Date parseDateFromHTMLToDateObject(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd")
                    .parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    private String validateVenueSearchInput(String venueLocation, String venueDate, String venueCapacity) {
        if(venueLocation.isEmpty() || venueDate.isEmpty() || venueCapacity.isEmpty())
            return "Es wurden nicht alle Parameter übergeben";

        if(!Arrays.asList("AUGSBURG", "INGOLSTADT", "MUNICH", "NUREMBURG", "REGENSBURG").contains(venueLocation))
            return "Ort wurde nicht richtig gewählt";

        var dateFormat = parseDateFromHTMLToDateObject(venueDate);
        if(dateFormat == null)
            return "Datum hat falsches Format";
        else if (!dateFormat.after(new Date()) || !dateFormat.before(parseDateFromHTMLToDateObject("2026-01-01")))
            return "Datum außerhalb des erlaubten Zeitraums (morgen - 31.12.2025)";

        if(!Arrays.asList("100", "500", "1000", "5000", "100000").contains(venueCapacity))
            return "Kapazität wurde nicht richtig gewählt";

        return null;
    }
}
