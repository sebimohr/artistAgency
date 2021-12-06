package de.othr.sw.mos.artistAgency.service.userService;

import de.othr.sw.mos.artistAgency.entity.ArtistDto;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceIF extends UserDetailsService {
    User registerUser(User user) throws UserServiceException;
    
    ArtistDto getArtistInformation(Long artistId);
}
