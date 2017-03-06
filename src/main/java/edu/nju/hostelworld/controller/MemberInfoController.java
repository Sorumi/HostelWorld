package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.MemberInfoBean;
import edu.nju.hostelworld.bean.OrderBean;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.MemberService;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/**
 * Created by Sorumi on 17/2/27.
 */

@Controller
@RequestMapping("/")
@SessionAttributes({"member"})
public class MemberInfoController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }
        member = memberService.findMemberByID(member.getID());
        model.addAttribute("member", member);

        MemberInfoBean memberInfoBean = memberService.convertToMemberInfoBean(member.getID());
        model.addAttribute("memberInfoBean", memberInfoBean);

        return "member-info";
    }

    @RequestMapping(value = "/info/edit", method = RequestMethod.GET)
    public String infoEditGet(ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        return "member-info-edit";
    }

    @RequestMapping(value = "/info/edit", method = RequestMethod.POST)
    public String infoEditPost(Member newMember, ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        String name = newMember.getName();
        String contact = newMember.getContact();
        String account = newMember.getAccount();

        member.setName(name);
        member.setContact(contact);
        member.setAccount(account);

        ResultMessage resultMessage = memberService.updateMember(member);
        if (resultMessage == ResultMessage.FAILED) {
            return "member-info-edit";
        }
        return "redirect:/info";
    }

    @RequestMapping(value = "/info/activate", method = RequestMethod.POST)
    public String infoActivate(ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        ResultMessage resultMessage = memberService.activate(member.getID());

//        MemberInfoBean memberInfoBean = memberService.convertToMemberInfoBean(member);
//        model.addAttribute("memberInfoBean", memberInfoBean);

        return "redirect:/info";
    }


    @RequestMapping(value = "/info/resume", method = RequestMethod.POST)
    public String infoResume(ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        ResultMessage resultMessage = memberService.resume(member.getID());

//        MemberInfoBean memberInfoBean = memberService.convertToMemberInfoBean(member);
//        model.addAttribute("memberInfoBean", memberInfoBean);

        return "redirect:/info";
    }

    @RequestMapping(value = "/info/stop", method = RequestMethod.POST)
    public String infoStop(ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        ResultMessage resultMessage = memberService.stop(member.getID());

//        MemberInfoBean memberInfoBean = memberService.convertToMemberInfoBean(member);
//        model.addAttribute("memberInfoBean", memberInfoBean);

        return "redirect:/info";
    }


}
