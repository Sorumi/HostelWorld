package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.AlertBean;
import edu.nju.hostelworld.bean.MemberInfoBean;
import edu.nju.hostelworld.bean.OrderBean;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.AccountService;
import edu.nju.hostelworld.service.MemberService;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

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

        member = memberService.findMemberByID(member.getID());

        String name = newMember.getName();
        String contact = newMember.getContact();
        String account = newMember.getAccount();

        ResultMessage resultMessage = accountService.checkAccount(account);
        if (resultMessage == ResultMessage.NOT_EXIST) {
            model.addAttribute("alert", "不存在该银行账号!");
            return "member-info-edit";
        }

        member.setName(name);
        member.setContact(contact);
        member.setAccount(account);

        resultMessage = memberService.updateMember(member);

        AlertBean alertBean = new AlertBean();

        if (resultMessage == ResultMessage.SUCCESS) {
            alertBean.setMessage("修改成功！");
            alertBean.setButton("查看");
        } else {
            alertBean.setMessage("修改失败！");
            alertBean.setButton("返回");
        }
        alertBean.setUrl("info");

        model.addAttribute("alertBean", alertBean);

        updateSessionMember(member.getID(), model);
        return "alert-href";
    }

    //    @ResponseBody
    @RequestMapping(value = "/info/deposit", method = RequestMethod.POST)
    public String deposit(@RequestParam double money, ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        ResultMessage resultMessage = memberService.deposit(member.getID(), money);

        AlertBean alertBean = new AlertBean();

        if (resultMessage == ResultMessage.SUCCESS) {
            alertBean.setMessage("充值成功！");
            alertBean.setButton("查看");
        } else {
            alertBean.setMessage("充值失败！");
            alertBean.setButton("返回");
        }
        alertBean.setUrl("info");

        model.addAttribute("alertBean", alertBean);

        updateSessionMember(member.getID(), model);
        return "alert-href";
    }

    @RequestMapping(value = "/info/exchange", method = RequestMethod.POST)
    public String exchange(@RequestParam int point, ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        ResultMessage resultMessage = memberService.exchangeMoney(member.getID(), point);

        AlertBean alertBean = new AlertBean();

        if (resultMessage == ResultMessage.SUCCESS) {
            alertBean.setMessage("兑换成功！");
            alertBean.setButton("查看");
        } else {
            alertBean.setMessage("兑换失败！");
            alertBean.setButton("返回");
        }
        alertBean.setUrl("info");

        model.addAttribute("alertBean", alertBean);

        updateSessionMember(member.getID(), model);
        return "alert-href";
    }

    @RequestMapping(value = "/info/activate", method = RequestMethod.POST)
    public String activate(ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        ResultMessage resultMessage = memberService.activate(member.getID());

        AlertBean alertBean = new AlertBean();

        if (resultMessage == ResultMessage.SUCCESS) {
            alertBean.setMessage("激活成功！");
            alertBean.setButton("查看");
        } else {
            alertBean.setMessage("激活失败！账户不足1000元！");
            alertBean.setButton("返回");
        }
        alertBean.setUrl("info");

        model.addAttribute("alertBean", alertBean);

        updateSessionMember(member.getID(), model);
        return "alert-href";
    }


    @RequestMapping(value = "/info/resume", method = RequestMethod.POST)
    public String resume(ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        ResultMessage resultMessage = memberService.resume(member.getID());

        AlertBean alertBean = new AlertBean();

        if (resultMessage == ResultMessage.SUCCESS) {
            alertBean.setMessage("恢复成功！");
            alertBean.setButton("查看");
        } else {
            alertBean.setMessage("恢复失败！");
            alertBean.setButton("返回");
        }
        alertBean.setUrl("info");

        model.addAttribute("alertBean", alertBean);

        updateSessionMember(member.getID(), model);
        return "alert-href";
    }

    @RequestMapping(value = "/info/stop", method = RequestMethod.POST)
    public String stop(ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        ResultMessage resultMessage = memberService.stop(member.getID());

        AlertBean alertBean = new AlertBean();

        if (resultMessage == ResultMessage.SUCCESS) {
            alertBean.setMessage("停止成功！");
            alertBean.setButton("查看");
        } else {
            alertBean.setMessage("停止失败！");
            alertBean.setButton("返回");
        }
        alertBean.setUrl("info");

        model.addAttribute("alertBean", alertBean);

        updateSessionMember(member.getID(), model);
        return "alert-href";
    }

    private void updateSessionMember(String memberID, ModelMap model) {
        Member member = memberService.findMemberByID(memberID);
        model.addAttribute("member", member);
    }
}
