package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.entity.ArtistDto;
import de.othr.sw.mos.artistAgency.exception.ArtistNotFoundException;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserServiceRestController {
    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = "/artist/{artistId}", method = RequestMethod.GET)
    public ArtistDto getArtistFromService(@PathVariable("artistId") Long artistId) throws ArtistNotFoundException {
        return userService.getArtistInformation(artistId);
    }
}
