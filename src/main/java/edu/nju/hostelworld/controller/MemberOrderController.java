package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.AlertBean;
import edu.nju.hostelworld.bean.OrderBean;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.MemberService;
import edu.nju.hostelworld.service.OrderService;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Sorumi on 17/2/26.
 */

@Controller
@RequestMapping("/")
@SessionAttributes({"member"})
public class MemberOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String orderList(@RequestParam(value = "state", required = false) String state, ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        OrderState orderState = null;
        if (state != null) {
            orderState = OrderState.getByString(state);
        }

        List<OrderBean> orderList = orderService.findMemberOrders(member.getID(), orderState);
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderState", orderState);

        model.addAttribute("title", "预定管理");
        model.addAttribute("nav", "nav-order");

        return "member-order-list";
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public String order(@PathVariable(value = "id") String ID, ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        OrderBean orderBean = orderService.findOrderByID(ID);
        if (orderBean == null) {
            model.addAttribute("alertMessage", "订单不存在！");
            return "alert";
        } else if (!orderBean.getBookOrder().getMemberID().equals(member.getID())) {
            model.addAttribute("alertMessage", "您无法查看此订单！");
            return "alert";
        }
        model.addAttribute("order", orderBean);

        model.addAttribute("title", "预定信息");
        model.addAttribute("nav", "nav-order");

        return "member-order-info";
    }

    @RequestMapping(value = "/order/{id}/cancel", method = RequestMethod.POST)
    public String orderCancel(@PathVariable(value = "id") String ID, ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member) model.get("member");
        }

        OrderBean orderBean = orderService.findOrderByID(ID);
        if (orderBean == null) {
            model.addAttribute("alertMessage", "订单不存在！");
            return "alert";
        } else if (!orderBean.getBookOrder().getMemberID().equals(member.getID())) {
            model.addAttribute("alertMessage", "您无法操作此订单！");
            return "alert";
        }

        ResultMessage resultMessage = orderService.cancelOrder(ID);

        AlertBean alertBean = new AlertBean();

        if (resultMessage == ResultMessage.SUCCESS) {
            alertBean.setMessage("退订成功！");
            alertBean.setButton("查看");
        } else {
            alertBean.setMessage("退订失败！");
            alertBean.setButton("返回");
        }
        alertBean.setUrl("order/" + ID);

        model.addAttribute("alertBean", alertBean);

        updateSessionMember(member.getID(), model);

        return "alert-href";
    }


    private void updateSessionMember(String memberID, ModelMap model) {
        Member member = memberService.findMemberByID(memberID);
        model.addAttribute("member", member);
    }
}
