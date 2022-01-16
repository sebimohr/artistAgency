package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.controller.util.ControllerTemplate;
import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

// controllers have a RequestMapping over the whole controller, so there's a mapping between different areas
// finance-area includes the finance-site for logged-in users
@Controller
@RequestMapping(value = "finance")
public class FinanceController extends ControllerTemplate {

    @RequestMapping(value = {"/myFinances", "/", ""}, method = RequestMethod.GET)
    public String ShowFinancesList(
            Model model,
            Principal principal
    ) {
        List<FinanceLog> financeLogList;
        try {
            financeLogList = financeService.getFinanceLogByUserId(
                    getCurrentlyLoggedInUser(principal).getID()
            );
        } catch (UserServiceException e) {
            return renderErrorPageOnException(model, e.getMessage());
        }

        model.addAttribute("financeLogs", financeLogList);

        return financeList;
    }
}
