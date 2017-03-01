package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.Bean.MemberRegisterBean;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.MemberService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by Sorumi on 17/2/25.
 */

@Controller
@RequestMapping("/")
@SessionAttributes({"member", "hostel"})
public class MemberAuthController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(ModelMap model) {
        boolean isLogin = model.containsAttribute("member");
        if (isLogin) {
            return "redirect:/home";
        }

        model.remove("alert");
        return "member-login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(MemberRegisterBean memberRegisterBean, HttpSession session, ModelMap model) {
        boolean isLogin = model.containsAttribute("member");
        if (isLogin) {
            return "redirect:/home";
        }

        String username = memberRegisterBean.getUsername();
        String password = memberRegisterBean.getPassword();

        if (!isValid(username) || !isValid(password)) {
            model.addAttribute("alert", "请将信息填写完整!");
            model.addAttribute("memberRegisterBean", memberRegisterBean);
            return "member-login";
        }

        ResultMessage resultMessage = memberService.checkMember(username, password);

        if (resultMessage == ResultMessage.NOT_EXIST) {
            model.addAttribute("alert", "用户名不存在！");
            model.remove("memberRegisterBean");
            return "member-login";

        } else if (resultMessage == ResultMessage.FAILED) {
            model.addAttribute("alert", "密码错误！");
            memberRegisterBean.setPassword("");
            model.addAttribute("memberRegisterBean", memberRegisterBean);
            return "member-login";
        }

        Member member = memberService.findMemberByUsername(username);
        session.removeAttribute("hostel");
        model.remove("hostel");
        model.addAttribute("member", member);

        return "redirect:/home";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGet(ModelMap model) {

        boolean isLogin = model.containsAttribute("member");
        if (isLogin) {
            return "redirect:/home";
        }

        model.remove("alert");

        return "member-register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(MemberRegisterBean memberRegisterBean, ModelMap model) {

        boolean isLogin = model.containsAttribute("member");
        if (isLogin) {
            return "redirect:/home";
        }

        String username = memberRegisterBean.getUsername();
        String password = memberRegisterBean.getPassword();
        String confirmedPassword = memberRegisterBean.getConfirmPassword();
        String name = memberRegisterBean.getName();

        if (!isValid(username) || !isValid(password) || !isValid(confirmedPassword) || !isValid(name)) {
            model.addAttribute("alert", "请将信息填写完整!");
            model.addAttribute("memberRegisterBean", memberRegisterBean);
            return "member-register";
        }
        if (!password.equals(confirmedPassword)) {
            model.addAttribute("alert", "两次密码输入不一致!");
            model.addAttribute("memberRegisterBean", memberRegisterBean);
            return "member-register";
        }
        if (memberService.findMemberByUsername(username) != null) {
            model.addAttribute("alert", "用户名已存在");
            model.addAttribute("memberRegisterBean", memberRegisterBean);
            return "member-register";
        }
        Member member = new Member();
        member.setName(name);
        member.setPassword(password);
        member.setUsername(username);

        ResultMessage resultMessage = memberService.addMember(member);

        if (resultMessage == ResultMessage.FAILED) {
            model.addAttribute("alertMessage", "注册失败！");
            return "alert";
        }
        model.addAttribute("member", member);

        return "redirect:/home";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, ModelMap model) {
//        boolean isLogin = model.containsAttribute("hostel");
//        if (!isLogin) {
//
//        }
        session.removeAttribute("member");
        model.remove("member");

        return "redirect:/login";
    }

    private boolean isValid(String keyword) {
        return keyword != null && !keyword.equals("");
    }

}
