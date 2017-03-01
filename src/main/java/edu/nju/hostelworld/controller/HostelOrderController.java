package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.Bean.OrderBean;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.service.OrderService;
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
@RequestMapping("/hostel")
@SessionAttributes({"hostel"})
public class HostelOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String orderList(@RequestParam(value = "state", required = false) String state, ModelMap model) {
        Hostel hostel;
        if (model.get("hostel") == null) {
            return "redirect:/hostel/login";
        } else {
            hostel = (Hostel) model.get("hostel");
        }

        OrderState orderState = null;
        if (state != null) {
            orderState = OrderState.getByString(state);
        }

        List<OrderBean> orderList = orderService.findHostelOrders(hostel.getID(), orderState);
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderState", orderState);

        return "hostel-order-list";
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public String order(@PathVariable(value = "id") String ID, ModelMap model) {
        Hostel hostel;
        if (model.get("hostel") == null) {
            return "redirect:/hostel/login";
        } else {
            hostel = (Hostel) model.get("hostel");
        }

        OrderBean orderBean = orderService.findOrderByID(ID);
        if (orderBean == null) {
            model.addAttribute("alertMessage", "订单不存在！");
            return "alert";
        } else if (!orderBean.getBookOrder().getHostelID().equals(hostel.getID())) {
            model.addAttribute("alertMessage", "您无法查看此订单！");
            return "alert";
        }
        model.addAttribute("order", orderBean);

        return "hostel-order-info";
    }

    @RequestMapping(value = "/order/{id}/checkin", method = RequestMethod.POST)
    public String orderCheckIn(@PathVariable(value = "id") String ID, ModelMap model) {
        Hostel hostel;
        if (model.get("hostel") == null) {
            return "redirect:/hostel/login";
        } else {
            hostel = (Hostel) model.get("hostel");
        }

        OrderBean orderBean = orderService.findOrderByID(ID);
        if (orderBean == null) {
            model.addAttribute("alertMessage", "订单不存在！");
            return "alert";
        } else if (!orderBean.getBookOrder().getHostelID().equals(hostel.getID())) {
            model.addAttribute("alertMessage", "您无法操作此订单！");
            return "alert";
        }

        ResultMessage resultMessage = orderService.checkInOrder(orderBean.getBookOrder().getID());
        System.out.println(resultMessage);

        return "redirect:/hostel/order/" + ID;
    }

}
