package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    Optional<Venue> findByID(Long venueId);
}
