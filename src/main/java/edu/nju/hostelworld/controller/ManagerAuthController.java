package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.Bean.MemberRegisterBean;
import edu.nju.hostelworld.model.Manager;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.ManagerService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by Sorumi on 17/2/28.
 */

@Controller
@RequestMapping("/admin")
@SessionAttributes({"manager", "member", "hostel"})
public class ManagerAuthController {

    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(ModelMap model) {
        boolean isLogin = model.containsAttribute("manager");
        if (isLogin) {
            return "redirect:/admin/application";
        }

        model.remove("alert");
        return "manager-login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(MemberRegisterBean memberRegisterBean, HttpSession session, ModelMap model) {
        boolean isLogin = model.containsAttribute("manager");
        if (isLogin) {
            return "redirect:/admin/application";
        }

        String username = memberRegisterBean.getUsername();
        String password = memberRegisterBean.getPassword();

        if (!isValid(username) || !isValid(password)) {
            model.addAttribute("alert", "请将信息填写完整!");
            model.addAttribute("memberRegisterBean", memberRegisterBean);
            return "manager-login";
        }

        ResultMessage resultMessage = managerService.checkManager(username, password);

        if (resultMessage == ResultMessage.NOT_EXIST) {
            model.addAttribute("alert", "用户名不存在！");
            model.remove("memberRegisterBean");
            return "manager-login";

        } else if (resultMessage == ResultMessage.FAILED) {
            model.addAttribute("alert", "密码错误！");
            memberRegisterBean.setPassword("");
            model.addAttribute("memberRegisterBean", memberRegisterBean);
            return "manager-login";
        }

        Manager manager = managerService.findManagerByUsername(username);
        session.removeAttribute("hostel");
        model.remove("hostel");
        session.removeAttribute("member");
        model.remove("member");

        model.addAttribute("manager", manager);

        return "redirect:/admin/application";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, ModelMap model) {

        session.removeAttribute("manager");
        model.remove("manager");

        return "redirect:/admin/login";
    }


    private boolean isValid(String keyword) {
        return keyword != null && !keyword.equals("");
    }

}
