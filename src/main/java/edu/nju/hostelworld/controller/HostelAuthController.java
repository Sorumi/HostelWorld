package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.HostelRegisterBean;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by Sorumi on 17/2/26.
 */

@Controller
@RequestMapping("/hostel")
@SessionAttributes({"hostel", "member"})
public class HostelAuthController {

    @Autowired
    private HostelService hostelService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String defaultGet(ModelMap model) {
        return "redirect:/hostel/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (isLogin) {
            return "redirect:/hostel/home";
        }

        model.remove("alert");
        return "hostel-login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(HostelRegisterBean hostelRegisterBean, HttpSession session, ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (isLogin) {
            return "redirect:/hostel/home";
        }

        String username = hostelRegisterBean.getUsername();
        String password = hostelRegisterBean.getPassword();

        if (!isValid(username) || !isValid(password)) {
            model.addAttribute("alert", "请将信息填写完整!");
            model.addAttribute("hostelRegisterBean", hostelRegisterBean);
            return "hostel-login";
        }

        ResultMessage resultMessage = hostelService.checkHostel(username, password);

        if (resultMessage == ResultMessage.NOT_EXIST) {
            model.addAttribute("alert", "用户名不存在！");
            model.remove("hostelRegisterBean");
            return "hostel-login";

        } else if (resultMessage == ResultMessage.FAILED) {
            model.addAttribute("alert", "密码错误！");
            hostelRegisterBean.setPassword("");
            model.addAttribute("hostelRegisterBean", hostelRegisterBean);
            return "hostel-login";
        }

        Hostel hostel = hostelService.findHostelByUsername(username);
        model.addAttribute("hostel", hostel);
        session.removeAttribute("member");
        model.remove("member");

        return "redirect:/hostel/home";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGet(ModelMap model) {

        boolean isLogin = model.containsAttribute("hostel");
        if (isLogin) {
            return "redirect:/hostel/home";
        }

        model.remove("alert");

        return "hostel-register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(HostelRegisterBean hostelRegisterBean, ModelMap model) {

        boolean isLogin = model.containsAttribute("hostel");
        if (isLogin) {
            return "redirect:/hostel/home";
        }

        String username = hostelRegisterBean.getUsername();
        String password = hostelRegisterBean.getPassword();
        String confirmedPassword = hostelRegisterBean.getConfirmPassword();

        if (!isValid(username) || !isValid(password) || !isValid(confirmedPassword)) {
            model.addAttribute("alert", "请将信息填写完整!");
            model.addAttribute("hostelRegisterBean", hostelRegisterBean);
            return "hostel-register";
        }
        if (!password.equals(confirmedPassword)) {
            model.addAttribute("alert", "两次密码输入不一致!");
            model.addAttribute("hostelRegisterBean", hostelRegisterBean);
            return "hostel-register";
        }
        if (hostelService.findHostelByUsername(username) != null) {
            model.addAttribute("alert", "用户名已存在！");
            model.addAttribute("hostelRegisterBean", hostelRegisterBean);
            return "hostel-register";
        }
        Hostel hostel = new Hostel();
        hostel.setPassword(password);
        hostel.setUsername(username);

        ResultMessage resultMessage = hostelService.addHostel(hostel);

        if (resultMessage == ResultMessage.FAILED) {
            model.addAttribute("alertMessage", "注册失败！");
            return "alert";
        }
        model.addAttribute("hostel", hostel);

        return "redirect:/hostel/home";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, ModelMap model) {
//        boolean isLogin = model.containsAttribute("hostel");
//        if (!isLogin) {
//
//        }
        session.removeAttribute("hostel");
        model.remove("hostel");

        return "redirect:/hostel/login";
    }

        private boolean isValid(String keyword) {
        return keyword != null && !keyword.equals("");
    }

}
