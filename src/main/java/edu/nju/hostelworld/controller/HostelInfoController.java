package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.util.HostelState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by Sorumi on 17/2/26.
 */

@Controller
@RequestMapping("/hostel")
@SessionAttributes({"hostel"})
public class HostelInfoController {

    @Autowired
    private HostelService hostelService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homeGet(ModelMap model) {

        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() != HostelState.Unopened) {
            model.addAttribute("hostel", hostel);
            return "hostel-info";
        }

        return "hostel-home";
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() == HostelState.Unopened) {
            return "redirect:/hostel/home";
        }

        model.addAttribute("hostel", hostel);
        return "hostel-info";
    }
}
