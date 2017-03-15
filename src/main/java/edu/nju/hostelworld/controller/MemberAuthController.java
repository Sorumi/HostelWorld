package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.AlertBean;
import edu.nju.hostelworld.bean.MemberRegisterBean;
import edu.nju.hostelworld.bean.PasswordEditBean;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String defaultGet(ModelMap model) {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(ModelMap model) {
        boolean isLogin = model.containsAttribute("member");
        if (isLogin) {
            return "redirect:/home";
        }

        model.remove("alert");

        model.addAttribute("title", "会员登录");

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

        model.addAttribute("title", "会员注册");

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
        session.removeAttribute("member");
        model.remove("member");

        return "redirect:/login";
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String passwordGet(ModelMap model) {
        if (model.get("member") == null) {
            return "redirect:/login";
        }

        return "member-password";
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String passwordPost(PasswordEditBean passwordEditBean, ModelMap model) {
        if (model.get("member") == null) {
            return "redirect:/login";
        }

        Member member = (Member) model.get("member");

        String oldPassword = passwordEditBean.getOldPassword();
        String newPassword = passwordEditBean.getNewPassword();
        String confirmPassword = passwordEditBean.getConfirmPassword();

        if (!isValid(oldPassword) || !isValid(newPassword) || !isValid(confirmPassword)) {
            model.addAttribute("alert", "请将信息填写完整!");
            return "member-password";
        }
        if (!oldPassword.equals(member.getPassword())) {
            model.addAttribute("alert", "原密码不正确!");
            return "member-password";
        }

        member.setPassword(newPassword);
        ResultMessage resultMessage = memberService.updateMember(member);

        AlertBean alertBean = new AlertBean();
        if (resultMessage == ResultMessage.SUCCESS) {
            alertBean.setMessage("修改成功！");
        } else {
            alertBean.setMessage("修改失败！");
        }
        alertBean.setUrl("info");
        alertBean.setButton("返回");

        model.addAttribute("alertBean", alertBean);

        model.addAttribute("title", "修改密码");
        model.addAttribute("nav", "nav-info");

        return "alert-href";

    }

    private boolean isValid(String keyword) {
        return keyword != null && !keyword.equals("");
    }

}
