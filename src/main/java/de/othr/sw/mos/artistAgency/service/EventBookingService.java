package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.Venue;
import de.othr.sw.mos.artistAgency.exception.EventServiceException;
import de.othr.sw.mos.artistAgency.repository.EventRepository;
import de.othr.sw.mos.artistAgency.repository.VenueRepository;
import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventBookingService implements EventBookingServiceIF {

    private final EventRepository eventRepo;
    private final VenueRepository venueRepo;

    @Autowired
    public EventBookingService(EventRepository eventRepo, VenueRepository venueRepo) {
        this.eventRepo = eventRepo;
        this.venueRepo = venueRepo;
    }

    @Override
    @Transactional
    public Event registerEvent(Event event) throws EventServiceException {
        var foundEventOptional = eventRepo.findByID(event.getID());

        if(foundEventOptional.isEmpty()) {
            var newEvent = new Event(
                    event.getVenueId(),
                    event.getArtistId(),
                    event.getEventDate(),
                    event.getEventName()
            );

            // TODO: RPC on ELM
            createBooking(newEvent, financeArtistAgencyId);

            return eventRepo.save(newEvent);
        }

        throw new EventServiceException("Event with ID already exists.");
    }

    @Override
    public List<Venue> getAllVenuesFromEventLocationManager() {
        // TODO: RPC on ELM
        return venueRepo.findAll();
    }

    @Override
    public Venue getSpecificVenueFromEventLocationManager(Long venueId) throws Exception {
        // TODO: RPC on ELM
        return venueRepo.findByID(venueId).orElseThrow( () ->
                new Exception("Venue with " + venueId + " not found"));
    }

    @Override
    public List<Event> getAllEventsForSpecificArtist(Long artistId) {
        return eventRepo.findAllByArtistId(artistId);
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
    private Event createBooking(Event event, Long financeArtistAgencyId) {
        return event;
    }

    @Override
    public Venue registerVenueForTesting(Venue venue) {
        return venueRepo.save(venue);
    }
}
