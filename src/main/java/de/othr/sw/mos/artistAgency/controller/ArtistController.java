package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping(value = "/artist")
public class ArtistController implements SitePathDistribution {

    @Autowired
    private UserServiceIF userService;

    @Autowired
    private EventBookingServiceIF eventService;

    @RequestMapping(value = {"/list", "/", ""}, method = RequestMethod.GET)
    public String ShowArtistsList(Model model) {
        var artistList = userService.getAllUsers();

        model.addAttribute("artists", artistList);
        return artistListSite;
    }

    @RequestMapping(value = "/details")
    public String ShowArtistPage(@RequestParam(value = "id") Long artistId, Model model) throws UserServiceException {
        if(artistId == null) {
            throw new UserServiceException("Empty id detected.");
        }

        var detailedArtist = userService.getUserByUserId(artistId);
        var artistEvents = eventService.getAllEventsForSpecificArtist(artistId);

        model.addAttribute("artist", detailedArtist);
        model.addAttribute("events", artistEvents);

        return artistDetailsSite;
    }

    @RequestMapping(value = "/myProfile", method = RequestMethod.GET)
    public String ShowMyProfile(Principal principal, Model model) {
        var currentUser = getCurrentlyLoggedInUser(principal);
        model.addAttribute("currentUser", currentUser);
        // show information on my profile page and make it editable on edit page
        return myProfileSite;
    }

    @RequestMapping(value = "/myProfile/edit", method = RequestMethod.GET)
    public String EditMyProfileSite(Principal principal, Model model) {
        var currentUser = getCurrentlyLoggedInUser(principal);
        model.addAttribute("currentUser", currentUser);
        return editMyProfileSite;
    }

    private User getCurrentlyLoggedInUser(Principal principal) {
        return userService.loadUserByUsername(principal.getName());
    }
}
