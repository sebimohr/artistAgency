package de.othr.sw.mos.artistAgency.service.interfaces;

import de.othr.sw.mos.artistAgency.entity.Event;
import de.othr.sw.mos.artistAgency.entity.Venue;

import java.util.List;

public interface EventBookingServiceIF {
    Event registerEvent(Event event);

    List<Venue> getAllVenuesFromEventLocationManager();

    Venue getSpecificVenueFromEventLocationManager(Long venueId);

    // TODO: replace with interface-method from ELM
    Event createBooking(Event event /*TODO: Long financeArtistAgencyId*/);

    Venue registerVenueForTesting(Venue venue);
}
