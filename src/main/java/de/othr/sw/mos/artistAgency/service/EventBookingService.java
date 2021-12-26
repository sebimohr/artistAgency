package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.Venue;
import de.othr.sw.mos.artistAgency.repository.EventRepository;
import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventBookingService implements EventBookingServiceIF {

    private final EventRepository eventRepo;

    @Autowired
    public EventBookingService(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }


    @Override
    public Event registerEvent(Event event) {
        return null;
    }

    @Override
    public List<Venue> getAllVenuesFromEventLocationManager() {
        return null;
    }

    @Override
    public Venue getSpecificVenueFromEventLocationManager(Long venueId) {
        return null;
    }

    @Override
    public Event createBooking(Event event) {
        return null;
    }

    @Override
    public Venue registerVenueForTesting(Venue venue) {
        return null;
    }
}
