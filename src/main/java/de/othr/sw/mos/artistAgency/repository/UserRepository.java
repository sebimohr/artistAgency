package de.othr.sw.mos.artistAgency.repository;

import de.othr.sw.mos.artistAgency.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByID(Long Id);

    Optional<User> findByUsername(String username);
}
