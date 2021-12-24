package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("eventBookingService")
public class EventBookingService implements EventBookingServiceIF {
}
