package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
}
