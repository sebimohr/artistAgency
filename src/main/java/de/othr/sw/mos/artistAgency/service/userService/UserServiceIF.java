package de.othr.sw.mos.artistAgency.service.userService;

import de.othr.sw.mos.artistAgency.entity.ArtistDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceIF extends UserDetailsService {
    ArtistDto getArtistInformation(Long artistId);
}
