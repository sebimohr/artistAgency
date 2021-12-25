package de.othr.sw.mos.artistAgency.controller;

import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import de.othr.sw.mos.artistAgency.service.interfaces.FinanceServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "finance")
public class FinanceController implements SitePathDistribution {

    @Autowired
    private FinanceServiceIF financeService;

    @RequestMapping(value = {"/list", "/", ""}, method = RequestMethod.GET)
    public String ShowFinancesList(Model model, Principal principal) {
        List<FinanceLog> financeLogList = financeService.getFinanceLogByUsername(principal.getName());

        model.addAttribute(financeLogList);

        return financeList;
    }
}
