package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.entity.ArtType;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping(value = "/artist")
public class ArtistController implements SitePathDistribution {

    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String ShowArtistsList(Model model) {
        List<User> artistList = userService.getAllUsers();

        model.addAttribute("artists", artistList);
        return artistListSite;
    }

    @RequestMapping(value = "/details")
    public String ShowArtistPage(@RequestParam(value = "id") String value, Model model) throws UserServiceException {
        if(value == null) {
            throw new UserServiceException("Empty id detected");
        }

        User detailedUser = userService.getUserByUsername(value);

        model.addAttribute(detailedUser);

        return artistDetailsSite;
    }
}
