package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.FinanceRecordBean;
import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.service.AppService;
import edu.nju.hostelworld.service.FinanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/**
 * Created by Sorumi on 17/3/11.
 */

@Controller
@RequestMapping("/admin")
@SessionAttributes({"manager"})
public class ManagerStatisticController {

    @Autowired
    private FinanceRecordService financeRecordService;

    @Autowired
    private AppService appService;

    @RequestMapping(value = "/statistic/money", method = RequestMethod.GET)
    public String statisticGet(ModelMap model) {

        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        App app = appService.findApp();
        model.addAttribute("app", app);

        List<FinanceRecordBean> financeRecords = financeRecordService.findFinanceManagerRecords();
        model.addAttribute("financeRecords", financeRecords);

        return "manager-statistic-money";
    }
}
