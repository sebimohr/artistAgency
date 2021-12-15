package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    // findByUsername -> automatic query for username / primary key
    Optional<User> findByUsername(String email);
}
