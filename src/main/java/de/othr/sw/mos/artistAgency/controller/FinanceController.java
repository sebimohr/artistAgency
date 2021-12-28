package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.service.interfaces.FinanceServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping(value = "finance")
public class FinanceController implements SitePathDistribution {

    @Autowired
    private FinanceServiceIF financeService;

    @Autowired
    private UserServiceIF userService;

    @RequestMapping(value = {"/myFinances", "/", ""}, method = RequestMethod.GET)
    public String ShowFinancesList(Model model, Principal principal) {
        var financeLogList = financeService.getFinanceLogByUserId(getCurrentlyLoggedInUserId(principal));

        model.addAttribute("financeLogs", financeLogList);

        return financeList;
    }

    private Long getCurrentlyLoggedInUserId(Principal principal) {
        return userService.loadUserByUsername(principal.getName()).getUserId();
    }
}
