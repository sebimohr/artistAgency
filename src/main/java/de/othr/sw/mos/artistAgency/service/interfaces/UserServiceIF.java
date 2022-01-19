package de.othr.sw.mos.artistAgency.service.interfaces;

import de.othr.sw.mos.artistAgency.entity.ArtistDto;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.ArtistNotFoundException;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserServiceIF extends UserDetailsService {
    void registerUser(User user) throws UserServiceException;

    void updateUser(User user) throws UserServiceException;

    List<User> getAllUsers();

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    User getUserByUserId(Long Id) throws UserServiceException;
    
    ArtistDto getArtistInformation(Long artistId) throws ArtistNotFoundException;
}
