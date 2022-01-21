package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.EventServiceException;
import de.othr.sw.mos.artistAgency.exception.FinanceServiceException;
import de.othr.sw.mos.artistAgency.repository.EventRepository;
import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.FinanceServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sw.oth.EventlocationManagment.entity.DTO.BookingDTO;
import sw.oth.EventlocationManagment.entity.DTO.VenueDTO;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EventBookingService implements EventBookingServiceIF {

    @Value("${EventLocationManagerPort}")
    private String EventLocationManagerPort;

    private final EventRepository eventRepo;

    private final FinanceServiceIF financeService;

    private final RestTemplate restServiceClient;

    @Autowired
    public EventBookingService(EventRepository eventRepo, FinanceServiceIF financeService, RestTemplate restServiceClient) {
        this.eventRepo = eventRepo;
        this.financeService = financeService;
        this.restServiceClient = restServiceClient;
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

            // after my application saves all necessary information, RPC on eventLocationManager
            createBookingOnEventLocationManager(newEvent);

            return newEvent;
        }

        throw new EventServiceException("Event mit ID " + event.getID() + " existiert schon.");
    }

    @Override
    public List<VenueDTO> getFilteredVenuesFromEventLocationManager(
            String city,
            LocalDate date,
            int numberOfGuests
    ) throws EventServiceException {

        var urlForELM = EventLocationManagerPort +
                "/restapi/venues?date=" + date +
                "&city=" + city +
                "&maxNumberOfGuests=" + numberOfGuests;

        try {
            var venues = restServiceClient
                    .getForObject(urlForELM,
                            VenueDTO[].class);

            if(venues == null)
                return Collections.emptyList();
            else
                return Arrays.asList(venues);
        } catch (RestClientException e) {
            throw new EventServiceException(e.getMessage());
        }

    }

    @Override
    public VenueDTO getSpecificVenueFromEventLocationManager(Long venueId) throws EventServiceException {
        var urlForELM = EventLocationManagerPort +
                "/restapi/venues/" + venueId;
        try {
            return restServiceClient
                    .getForObject(urlForELM,
                            VenueDTO.class);
        } catch (RestClientException e) {
            throw new EventServiceException(e.getMessage());
        }
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

    private void createBookingOnEventLocationManager(Event event) throws EventServiceException {
        var bookingDto = new BookingDTO();
        bookingDto.setDate(event.getEventDate());
        bookingDto.setArtistID(event.getArtist().getID());
        bookingDto.setEventName(event.getEventName());
        bookingDto.setArtistAgencyName("ArtistAgencyMOS");
        bookingDto.setVenueID(event.getVenueId());
        bookingDto.setArtistName(event.getArtist().getArtistName());
        bookingDto.setArtistCost(event.getArtist().getSalaryPerEvent());

        var UrlForELM = EventLocationManagerPort + "/restapi/bookings";

        try {
            restServiceClient.postForObject(
                    UrlForELM,
                    bookingDto,
                    BookingDTO.class);
        } catch (RestClientException e) {
            throw new EventServiceException(e.getMessage());
        }
    }
}
