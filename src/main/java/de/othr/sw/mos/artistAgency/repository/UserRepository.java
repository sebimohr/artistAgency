package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    // findByEMAIL --> automatische Query für email
    Optional<User> findByUsername(String email);
}
