package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/artist")
public class ArtistController implements SitePathDistribution {

    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String ShowArtistsList(Model model) {
        var artistList = userService.getAllUsers();

        model.addAttribute("artists", artistList);
        return artistListSite;
    }

    @RequestMapping(value = "/details")
    public String ShowArtistPage(@RequestParam(value = "id") String artistId, Model model) throws UserServiceException {
        if(artistId == null) {
            throw new UserServiceException("Empty id detected");
        }

        var detailedUser = userService.getUserByUsername(artistId);

        model.addAttribute(detailedUser);

        return artistDetailsSite;
    }
}
