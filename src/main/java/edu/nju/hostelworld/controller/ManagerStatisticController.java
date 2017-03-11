package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.FinanceRecordBean;
import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.service.AppService;
import edu.nju.hostelworld.service.FinanceRecordService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @RequestMapping(value = "/commission", method = RequestMethod.POST)
    public String commissionPost(@RequestParam double commission, ModelMap model) {
        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        System.out.print(commission);

        ResultMessage resultMessage = appService.updateCommission(commission);
        return resultMessage.toString();
    }

    @RequestMapping(value = "/statistic/money", method = RequestMethod.GET)
    public String statisticMoney(ModelMap model) {

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
