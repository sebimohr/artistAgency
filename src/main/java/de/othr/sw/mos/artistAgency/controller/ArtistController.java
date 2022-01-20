package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.controller.util.ControllerTemplate;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.security.Principal;

// controllers have a RequestMapping over the whole controller, so there's a mapping between different areas
// artist-area includes everything where you can show a profile, like the artistList, myProfile site etc.
@Controller
@RequestMapping(value = "/artist")
public class ArtistController extends ControllerTemplate {

    @RequestMapping(value = {"/list", "/", ""}, method = RequestMethod.GET)
    public String ShowArtistsList(
            Model model
    ) {
        // add list of all registered users to site
        var artistList = userService.getAllUsers();

        model.addAttribute("artists", artistList);
        return artistListSite;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String ShowArtistPage(
            Model model,
            @RequestParam(value = "id") String artistId
    ) {
        long artistIdLong;
        try {
            artistIdLong = ParseAndValidateIdFromUrlParameter(artistId);
        } catch (ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return ShowArtistsList(model);
        }

        // try to get user from database with ID
        User detailedArtist;
        try {
            detailedArtist = userService.getUserByUserId(artistIdLong);
            model.addAttribute("artist", detailedArtist);
        } catch (UserServiceException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return ShowArtistsList(model);
        }

        // add events from artist to his detailsSite
        var artistEvents = eventService.getAllEventsForSpecificArtist(detailedArtist);
        model.addAttribute("events", artistEvents);

        return artistDetailsSite;
    }

    @RequestMapping(value = "/myProfile", method = RequestMethod.GET)
    public String ShowMyProfile(
            Model model,
            Principal principal
    ) {
        User currentUser;
        try {
            currentUser = getCurrentlyLoggedInUser(principal);
        } catch (UserServiceException e) {
            return renderErrorPageOnException(model, e.getMessage());
        }

        model.addAttribute("currentUser", currentUser);
        return myProfileSite;
    }

    @RequestMapping(value = "/myProfile/edit", method = RequestMethod.GET)
    public String EditMyProfileSite(
            Model model,
            Principal principal
    ) {
        User currentUser;
        try {
            currentUser = getCurrentlyLoggedInUser(principal);
        } catch (UserServiceException e) {
            return renderErrorPageOnException(model, e.getMessage());
        }

        model.addAttribute("currentUser", currentUser);

        return editMyProfileSite;
    }

    @RequestMapping(value = "/myProfile/edit", method = RequestMethod.POST)
    public String UpdateMyProfile(
            Model model,
            Principal principal,
            @ModelAttribute("currentUser") User userUpdated
    ){
        try {
            userUpdated.setID(getCurrentlyLoggedInUser(principal).getID());
            userService.updateUser(userUpdated);
        } catch (UserServiceException e) {
            return renderErrorPageOnException(model, e.getMessage());
        }

        // redirects back to myProfile
        return "redirect:/artist/myProfile";
    }
}
