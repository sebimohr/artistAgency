package de.othr.sw.mos.artistAgency.controller.util;

import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.FinanceServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;

import javax.xml.bind.ValidationException;
import java.security.Principal;

// standard template for controllers, includes services and methods for every controller
public abstract class ControllerTemplate implements SitePathDistribution {
    @Autowired
    protected UserServiceIF userService;

    @Autowired
    protected EventBookingServiceIF eventService;

    @Autowired
    protected FinanceServiceIF financeService;

    protected User getCurrentlyLoggedInUser(Principal principal) throws UserServiceException {
        try {
            return userService.loadUserByUsername(principal.getName());
        } catch (UsernameNotFoundException e) { // UsernameNotFoundException to UserServiceException
            // throw exception when loadByUsername fails -> shouldn't be possible with principal.getName()
            throw new UserServiceException(e.getMessage());
        }
    }

    // render errorPage with errorMessage -> only on fatal errors
    protected String renderErrorPageOnException(Model model, String message) {
        model.addAttribute("message", message);
        return errorSite;
    }

    protected Long ParseAndValidateIdFromUrlParameter(String id) throws ValidationException {
        // validate input before accessing database
        if(id == null) {
            throw new ValidationException("Leere ID angegeben.");
        }

        // parse String from URL parameter
        try {
            return Long.parseLong(id);
        } catch (Exception e) {
            throw new ValidationException("ID " + id + " ist keine Zahl.");
        }
    }
}
