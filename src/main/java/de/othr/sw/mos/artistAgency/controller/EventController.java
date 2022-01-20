package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.controller.util.ControllerTemplate;
import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.EventServiceException;
import de.othr.sw.mos.artistAgency.exception.InputValidationException;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sw.oth.EventlocationManagment.entity.DTO.VenueDTO;

import javax.xml.bind.ValidationException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// controllers have a RequestMapping over the whole controller, so there's a mapping between different areas
// event-area includes everything event-related showing events and booking them
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
    public String MyEventsSite(
            Model model,
            Principal principal
    ) {
        User currentUser;
        try {
            currentUser = getCurrentlyLoggedInUser(principal);
        } catch (UserServiceException e) {
            return renderErrorPageOnException(model, e.getMessage());
        }

        var artistEventList = eventService.getAllEventsForSpecificArtist(currentUser);
        model.addAttribute("events", artistEventList);
        return eventListSite;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String EventDetailsSite(
            Model model,
            @RequestParam(value = "id") String eventId
    ) {
        long eventIdLong;
        try {
            eventIdLong = ParseAndValidateIdFromUrlParameter(eventId);
        } catch (ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return ShowDefaultEventList(model);
        }

        // try to get event from database & venue from EventLocationManager
        Event detailedEvent;
        try {
            detailedEvent = eventService.getEventByEventId(eventIdLong);
            model.addAttribute("event", detailedEvent);
        } catch (EventServiceException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return ShowDefaultEventList(model);
        }

        // try to get venue from EventLocationManager
        VenueDTO detailedVenue;
        try {
            detailedVenue = eventService.getSpecificVenueFromEventLocationManager(detailedEvent.getVenueId());
            model.addAttribute("venue", detailedVenue);
        } catch (EventServiceException e) {
            model.addAttribute("errorMessage", "Leider konnten wir die Location nicht abrufen. Fehlermeldung: "
                    + e.getMessage());
        }

        return eventDetailsSite;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String EventBookingSite(
            Model model,
            @RequestParam(required = false) String venueLocation,
            @RequestParam(required = false) String venueDate,
            @RequestParam(required = false) String venueCapacity
    ) {
        // venueLocation, -Date and -Capacity are set when the search for venues is submitted
        List<VenueDTO> venueList = Collections.emptyList();

        if(venueLocation != null && venueDate != null && venueCapacity != null) {
            try {
                // validating the input from URL
                venueLocation = venueLocation.toUpperCase();
                validateVenueSearchInput(venueLocation, venueDate, venueCapacity);

                // parse eventDate to right format and add it to event-object
                var eventDate = parseDateFromHTMLToDateObject(venueDate);

                // parse eventCapacity to int
                // default value can't be reached because of validation (only included for compiler)
                var eventCapacity = switch (venueCapacity) {
                    case "100" -> 100;
                    case "500" -> 500;
                    case "1000" -> 1000;
                    case "5000" -> 5000;
                    case "100000" -> 100000;
                    default -> null;
                };

                if(eventCapacity != null){
                    try {
                        venueList = eventService.getFilteredVenuesFromEventLocationManager(venueLocation, eventDate, eventCapacity);
                    } catch (EventServiceException e) {
                        model.addAttribute("errorMessage", "Leider konnten wir die Locations nicht abrufen. Fehlermeldung: " + e.getMessage());
                    }
                }

                model.addAttribute("venues", venueList);

                // add attributes back to model, so that they stay selected on reload -> thymeleaf selects options based on modelAttributes
                model.addAttribute("venueLocation", venueLocation);
                model.addAttribute("venueDate", venueDate);
                model.addAttribute("venueCapacity", venueCapacity);

                var newEvent = new Event();

                newEvent.setEventDate(eventDate);

                model.addAttribute("event", newEvent);
                return bookNewEventSite;
            } catch (InputValidationException e) {
                model.addAttribute("errorMessage", e.getMessage() + ", bitte Suche erneut ausführen.");
            }
        }

        // standard attributes -> thymeleaf selects options based on modelAttributes
        model.addAttribute("venues", venueList);
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
        // venueId should always be set, only isn't set when HTML gets manipulated
        if(event.getVenueId() == null) {
            model.addAttribute("event", event);
            model.addAttribute("errorMessage", "Die Location wurde nicht richtig gesetzt, bitte versuchen Sie es noch einmal!");
            return bookNewEventSite;
        } else {
            try {
                event.setArtist(getCurrentlyLoggedInUser(principal));
                eventService.registerEvent(event);
                return "redirect:/event/myEvents";
            } catch (EventServiceException e) {
                return renderErrorPageOnException(model, "Event konnte nicht angelegt werden! Fehlermeldung: " + e.getMessage());
            } catch (UserServiceException e) {
                return renderErrorPageOnException(model, e.getMessage());
            }
        }
    }

    // parses Date from String
    private LocalDate parseDateFromHTMLToDateObject(String date) {
        return LocalDate.parse(date);
    }

    // validation of search input
    private void validateVenueSearchInput(String venueLocation, String venueDate, String venueCapacity) throws InputValidationException {
        if(venueLocation.isEmpty()
                || venueDate.isEmpty()
                || venueCapacity.isEmpty())
            throw new InputValidationException("Es wurden nicht alle Parameter übergeben");

        if(!Arrays.asList("AUGSBURG", "INGOLSTADT", "MUNICH", "NUREMBURG", "REGENSBURG").contains(venueLocation))
            throw new InputValidationException("Ort wurde nicht richtig gewählt");

        var dateFormat = parseDateFromHTMLToDateObject(venueDate);
        if(dateFormat == null)
            throw new InputValidationException("Datum hat falsches Format");

        else if (!dateFormat.isAfter(LocalDate.now()) || !dateFormat.isBefore(LocalDate.parse(("2026-01-01"))))
            throw new InputValidationException("Datum außerhalb des erlaubten Zeitraums (morgen - 31.12.2025)");

        if(!Arrays.asList("100", "500", "1000", "5000", "100000").contains(venueCapacity))
            throw new InputValidationException("Kapazität wurde nicht richtig gewählt");
    }
}
