package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.*;
import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.service.OrderService;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sorumi on 17/2/27.
 */

@Controller
@RequestMapping("/hostel")
@SessionAttributes({"hostel", "hostelBookOrderBean"})
public class HostelOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HostelService hostelService;

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

        ResultMessage resultMessage = orderService.checkInOrder(ID);
        System.out.println(resultMessage);

        return "redirect:/hostel/order/" + ID;
    }

    @RequestMapping(value = "/order/{id}/checkout", method = RequestMethod.POST)
    public String orderCheckOut(@PathVariable(value = "id") String ID, ModelMap model) {
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

        ResultMessage resultMessage = orderService.checkOutOrder(ID);
        System.out.println(resultMessage);

        return "redirect:/hostel/order/" + ID;
    }

    @RequestMapping(value = "/order/{id}/cancel", method = RequestMethod.POST)
    public String orderCancel(@PathVariable(value = "id") String ID, ModelMap model) {
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
        } else if (!orderBean.getBookOrder().getHostelID().equals(hostel.getID()) || orderBean.getBookOrder().getMemberID() != null) {
            model.addAttribute("alertMessage", "您无法操作此订单！");
            return "alert";
        }

        ResultMessage resultMessage = orderService.cancelOrder(ID);
        System.out.println(resultMessage);

        return "redirect:/hostel/order/" + ID;
    }

    @RequestMapping(value = "/order/book", method = RequestMethod.GET)
    public String orderBookGet(ModelMap model) {
        Hostel hostel;
        if (model.get("hostel") == null) {
            return "redirect:/hostel/login";
        } else {
            hostel = (Hostel) model.get("hostel");
        }
        HostelBookOrderBean hostelBookOrderBean = new HostelBookOrderBean();
        hostelBookOrderBean.setCheckInDate(LocalDate.now().toString());
        hostelBookOrderBean.setCheckOutDate(LocalDate.now().plusDays(1).toString());

        List list = hostelService.getRoomStockByCheckDate(hostel.getID(),
                LocalDate.parse(hostelBookOrderBean.getCheckInDate()),
                LocalDate.parse(hostelBookOrderBean.getCheckOutDate()));

        hostelBookOrderBean.setRoomStocks(list);

        model.addAttribute("hostelBookOrderBean", hostelBookOrderBean);

        return "hostel-order-book";
    }

    @RequestMapping(value = "/order/book", method = RequestMethod.POST)
    public String orderBookPost(HostelBookOrderBean hostelBookOrderBean, HttpSession session, ModelMap model) {
        Hostel hostel;
        if (model.get("hostel") == null) {
            return "redirect:/hostel/login";
        } else {
            hostel = (Hostel) model.get("hostel");
        }
        HostelBookOrderBean oldBean = (HostelBookOrderBean) model.get("hostelBookOrderBean");
        hostelBookOrderBean.setRoomStocks(oldBean.getRoomStocks());
        //
        OrderBean orderBean = orderService.generateOrder(hostelBookOrderBean, hostel);
        ResultMessage resultMessage = orderService.addNewOrder(orderBean);
        if (resultMessage == ResultMessage.SUCCESS) {
            session.removeAttribute("hostelBookOrderBean");

            model.addAttribute("alertMessage", "预订成功！");
        } else {
            model.addAttribute("alertMessage", "预订失败！");
        }

        return "alert";
    }

    @RequestMapping(value = "/roomstock", method = RequestMethod.POST)
    @ResponseBody
    public List hostelRoomStocks(@RequestBody SearchRoomJsonBean searchRoomJsonBean, HttpSession session, ModelMap model) {
        Hostel hostel;
        if (model.get("hostel") == null) {
            return null;
        } else {
            hostel = (Hostel) model.get("hostel");
        }
        List list = hostelService.getRoomStockByCheckDate(hostel.getID(),
                LocalDate.parse(searchRoomJsonBean.getCheckInDate()),
                LocalDate.parse(searchRoomJsonBean.getCheckOutDate()));

        HostelBookOrderBean hostelBookOrderBean = (HostelBookOrderBean) session.getAttribute("hostelBookOrderBean");
        hostelBookOrderBean.setRoomStocks(list);
        model.addAttribute("hostelBookOrderBean", hostelBookOrderBean);

        return list;
    }
}
