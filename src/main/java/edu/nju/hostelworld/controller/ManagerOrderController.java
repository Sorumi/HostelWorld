package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.AlertBean;
import edu.nju.hostelworld.bean.OrderBean;
import edu.nju.hostelworld.service.OrderService;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Sorumi on 17/3/1.
 */

@Controller
@RequestMapping("/admin")
@SessionAttributes({"manager"})
public class ManagerOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String orderList(@RequestParam(value = "state", required = false) String state, ModelMap model) {

        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        OrderState orderState = null;
        if (state != null) {
            orderState = OrderState.getByString(state);
        }

        List<OrderBean> orderList = orderService.findOrderByOrderState(orderState);
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderState", orderState);

        return "manager-order-list";
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public String order(@PathVariable(value = "id") String ID, ModelMap model) {

        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        OrderBean orderBean = orderService.findOrderByID(ID);
        if (orderBean == null) {
            model.addAttribute("alertMessage", "订单不存在！");
            return "alert";
        }
        model.addAttribute("order", orderBean);

        return "manager-order-info";
    }
    @RequestMapping(value = "/order/{id}/account", method = RequestMethod.POST)
    public String orderAccount(@PathVariable(value = "id") String ID, ModelMap model) {

        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        OrderBean orderBean = orderService.findOrderByID(ID);
        if (orderBean == null) {
            model.addAttribute("alertMessage", "订单不存在！");
            return "alert";
        }

        ResultMessage resultMessage = orderService.accountOrder(orderBean.getBookOrder().getID());
        System.out.println(resultMessage);

        AlertBean alertBean = new AlertBean();

        if (resultMessage == ResultMessage.SUCCESS) {
            alertBean.setMessage("结算成功！");
            alertBean.setButton("查看");
        } else {
            alertBean.setMessage("结算失败！");
            alertBean.setButton("返回");
        }
        alertBean.setUrl("/admin/order/" + ID);

        model.addAttribute("alertBean", alertBean);

        return "alert-href";
    }


}
