package de.othr.sw.mos.artistAgency.service.interfaces;

import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.EventServiceException;
import sw.oth.EventlocationManagment.entity.DTO.VenueDTO;

import java.time.LocalDate;
import java.util.List;

public interface EventBookingServiceIF {
    void registerEvent(Event event) throws EventServiceException;

    List<VenueDTO> getFilteredVenuesFromEventLocationManager(
            String city,
            LocalDate date,
            int numberOfGuests
    ) throws EventServiceException;

    VenueDTO getSpecificVenueFromEventLocationManager(Long venueId) throws EventServiceException;

    List<Event> getAllEventsForSpecificArtist(User artist);

    List<Event> getAllEvents();

    Event getEventByEventId(Long eventId) throws EventServiceException;
}
