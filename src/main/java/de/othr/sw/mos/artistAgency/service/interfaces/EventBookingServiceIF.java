package de.othr.sw.mos.artistAgency.service.interfaces;

import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.entity.Venue;
import de.othr.sw.mos.artistAgency.exception.EventServiceException;

import java.util.List;

public interface EventBookingServiceIF {
    Long financeArtistAgencyId = 1L;

    Event registerEvent(Event event) throws EventServiceException;

    List<Venue> getAllVenuesFromEventLocationManager();

    Venue getSpecificVenueFromEventLocationManager(Long venueId) throws Exception;

    List<Event> getAllEventsForSpecificArtist(User artist);

    List<Event> getAllEvents();

    Event getEventByEventId(Long eventId) throws EventServiceException;

    Venue registerVenueForTesting(Venue venue);
}
