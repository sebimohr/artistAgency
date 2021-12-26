package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // findByUsername -> automatic query for username / primary key
    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(Long Id);
}
