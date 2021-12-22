package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
