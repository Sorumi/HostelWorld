package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.Bean.ApplicationBean;
import edu.nju.hostelworld.model.Application;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.service.ApplicationService;
import edu.nju.hostelworld.util.ApplicationType;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Sorumi on 17/2/28.
 */

@Controller
@RequestMapping("/admin")
@SessionAttributes({"manager"})
public class ManagerApplicationController {

    @Autowired
    ApplicationService applicationService;

    @RequestMapping(value = "/application", method = RequestMethod.GET)
    public String applicationList(@RequestParam(value = "type", required = false) String type, ModelMap model) {
        boolean isLogin = model.containsAttribute("manager");
        if (!isLogin) {
            return "redirect:/admin/login";
        }

        ApplicationType applicationType = null;

        if (type != null) {
            applicationType = ApplicationType.getByString(type);
        }
        List<Application> applicationList = applicationService.findApplicationsByType(applicationType);
        model.addAttribute("applicationList", applicationList);
        model.addAttribute("applicationType", applicationType);

        return "manager-application-list";
    }

    @RequestMapping(value = "/application/{id}", method = RequestMethod.GET)
    public String application(@PathVariable(value = "id") String ID, ModelMap model) {
        boolean isLogin = model.containsAttribute("manager");
        if (!isLogin) {
            return "redirect:/admin/login";
        }
        Application application = applicationService.findApplicationByID(ID);
        if (application == null) {
            model.addAttribute("alertMessage", "申请不存在！");
            return "alert";
        }
        ApplicationBean applicationBean = applicationService.convertToApplicationBean(ID);

        model.addAttribute("applicationBean", applicationBean);

        return "manager-application-info";
    }

    @RequestMapping(value = "/application/{id}/pass", method = RequestMethod.POST)
    public String applicationPass(@PathVariable(value = "id") String ID, ModelMap model) {
        boolean isLogin = model.containsAttribute("manager");
        if (!isLogin) {
            return "redirect:/admin/login";
        }
        Application application = applicationService.findApplicationByID(ID);
        if (application == null) {
            model.addAttribute("alertMessage", "申请不存在！");
            return "alert";
        }
        ResultMessage resultMessage = applicationService.passApplication(ID);

        return "redirect:/admin/application/" + ID;
    }

    @RequestMapping(value = "/application/{id}/fail", method = RequestMethod.POST)
    public String applicationFail(@PathVariable(value = "id") String ID, ModelMap model) {
        boolean isLogin = model.containsAttribute("manager");
        if (!isLogin) {
            return "redirect:/admin/login";
        }
        Application application = applicationService.findApplicationByID(ID);
        if (application == null) {
            model.addAttribute("alertMessage", "申请不存在！");
            return "alert";
        }
        ResultMessage resultMessage = applicationService.failApplication(ID);

        return "redirect:/admin/application/" + ID;
    }

}