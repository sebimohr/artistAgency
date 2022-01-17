package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.entity.Venue;
import de.othr.sw.mos.artistAgency.exception.EventServiceException;
import de.othr.sw.mos.artistAgency.exception.FinanceServiceException;
import de.othr.sw.mos.artistAgency.repository.EventRepository;
import de.othr.sw.mos.artistAgency.repository.VenueRepository;
import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.FinanceServiceIF;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sw.oth.EventlocationManagment.entity.DTO.BookingDTO;
import sw.oth.EventlocationManagment.entity.DTO.VenueDTO;

import javax.transaction.Transactional;
import java.awt.print.Book;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class EventBookingService implements EventBookingServiceIF {

    @Autowired
    private RestTemplate restServiceClient;

    @Value("${EventLocationManagerPort}")
    private String EventLocationManagerPort;

    private final EventRepository eventRepo;
    private final VenueRepository venueRepo;

    private final FinanceServiceIF financeService;

    @Autowired
    public EventBookingService(EventRepository eventRepo, VenueRepository venueRepo, FinanceServiceIF financeService) {
        this.eventRepo = eventRepo;
        this.venueRepo = venueRepo;
        this.financeService = financeService;
    }

    @Override
    @Transactional(rollbackOn = EventServiceException.class)
    public Event registerEvent(Event event) throws EventServiceException {
        var foundEventOptional = eventRepo.findByID(event.getID());

        if(foundEventOptional.isEmpty()) {
            var newEvent = new Event(
                    event.getVenueId(),
                    event.getArtist(),
                    event.getEventDate(),
                    event.getEventName()
            );

            newEvent = eventRepo.save(newEvent);

// TODO:            createBookingOnEventLocationManager(newEvent);

            // register new financeLog for every Event created
            var financeLog = new FinanceLog(
                    event.getArtist(),
                    newEvent
            );

            try {
                financeService.registerFinanceLog(financeLog);
            } catch (FinanceServiceException e) {
                throw new EventServiceException(e.getMessage());
            }

            return newEvent;
        }

        throw new EventServiceException("Event mit ID " + event.getID() + " existiert schon.");
    }

    @Override
    public List<Venue> getFilteredVenuesFromEventLocationManager() throws EventServiceException {
        var city = "AUGSBURG";
        var date = LocalDate.now();
        var numberOfGuests = 100;

        var urlForELM = "http://im-codd:" + EventLocationManagerPort +
                "/restapi/venues?date=" + date +
                "&city=" + city +
                "&maxNumberOfGuests" + numberOfGuests;

        return venueRepo.findAll();

        // TODO: RPC!
        /*try {
            var venue = restServiceClient
                    .getForObject(urlForELM,
                            VenueDTO[].class);

            return Arrays.asList(venue);
        } catch (RestClientException e) {
            throw new EventServiceException(e.getMessage());
        }*/

    }

    @Override
    public Venue getSpecificVenueFromEventLocationManager(Long venueId) throws Exception {
        // TODO: RPC on ELM
        return venueRepo.findByID(venueId).orElseThrow( () ->
                new Exception("Venue mit ID " + venueId + " nicht gefunden!"));
    }

    @Override
    public List<Event> getAllEventsForSpecificArtist(User artist) {
        return eventRepo.findAllByArtist(artist);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @Override
    public Event getEventByEventId(Long eventId) throws EventServiceException {
        return eventRepo.findByID(eventId).orElseThrow( () ->
                new EventServiceException("Event with Id " + eventId + " not found."));
    }

    // TODO: only for testing
    private Event createBookingOnEventLocationManager(Event event) throws EventServiceException {
        var bookingDto = new BookingDTO();
        bookingDto.setDate(event.getEventDate());
        bookingDto.setArtistID(event.getArtist().getID());
        bookingDto.setEventName(event.getEventName());
        bookingDto.setArtistAgencyName("ArtistAgency");
        bookingDto.setVenue(event.getVenueId());

        var UrlForElm = "http://im-codd:" + EventLocationManagerPort + "/restapi/bookings";

        try {
            restServiceClient.postForObject(
                    UrlForElm,
                    bookingDto,
                    BookingDTO.class);

            return event;
        } catch (RestClientException e) {
            throw new EventServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Venue registerVenueForTesting(Venue venue) {
        return venueRepo.save(venue);
    }
}
