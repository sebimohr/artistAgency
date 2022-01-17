package de.othr.sw.mos.artistAgency.service.interfaces;

import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.entity.Venue;
import de.othr.sw.mos.artistAgency.exception.EventServiceException;

import java.util.List;

public interface EventBookingServiceIF {
    Event registerEvent(Event event) throws EventServiceException;

    List<Venue> getFilteredVenuesFromEventLocationManager() throws EventServiceException;

    Venue getSpecificVenueFromEventLocationManager(Long venueId) throws Exception;

    List<Event> getAllEventsForSpecificArtist(User artist);

    List<Event> getAllEvents();

    Event getEventByEventId(Long eventId) throws EventServiceException;

    Venue registerVenueForTesting(Venue venue);
}
