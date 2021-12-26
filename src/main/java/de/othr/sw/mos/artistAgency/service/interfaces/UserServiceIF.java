package de.othr.sw.mos.artistAgency.service.interfaces;

import de.othr.sw.mos.artistAgency.entity.ArtistDto;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceIF extends UserDetailsService {
    User registerUser(User user) throws UserServiceException;

    List<User> getAllUsers();

    @Override
    User loadUserByUsername(String username);

    User getUserByUserId(Long Id);
    
    ArtistDto getArtistInformation(Long artistId);
}
