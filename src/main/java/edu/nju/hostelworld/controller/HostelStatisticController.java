package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.HostelSearchRoomBean;
import edu.nju.hostelworld.bean.SearchRoomJsonBean;
import edu.nju.hostelworld.bean.StatisticOrderBean;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.service.OrderService;
import edu.nju.hostelworld.util.HostelState;
import edu.nju.hostelworld.util.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sorumi on 17/3/5.
 */

@Controller
@RequestMapping("/hostel")
@SessionAttributes({"hostel"})
public class HostelStatisticController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public String statisticGet(ModelMap model) {

        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() == HostelState.Unopened) {
            return "redirect:/hostel/home";
        }

        return "hostel-statistic";
    }

    @RequestMapping(value = "/statistic/{month}", method = RequestMethod.POST)
    @ResponseBody
    public StatisticOrderBean statisticMonth(@PathVariable String month, ModelMap model) {

        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return null;
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() == HostelState.Unopened) {
            return null;
        }

        int[] booked = orderService.countHostelOrdersByStateAndMonth(hostel.getID(), null, month);
        int[] checkIn = orderService.countHostelOrdersByStateAndMonth(hostel.getID(), OrderState.CheckIn, month);
        int[] checkOut = orderService.countHostelOrdersByStateAndMonth(hostel.getID(), OrderState.CheckOut, month);
        int[] cancelled = orderService.countHostelOrdersByStateAndMonth(hostel.getID(), OrderState.Cancelled, month);
        int[] expired = orderService.countHostelOrdersByStateAndMonth(hostel.getID(), OrderState.Expired, month);
        StatisticOrderBean statisticOrderBean = new StatisticOrderBean();
        statisticOrderBean.setBooked(booked);
        statisticOrderBean.setCheckIn(checkIn);
        statisticOrderBean.setCheckOut(checkOut);
        statisticOrderBean.setCancelled(cancelled);
        statisticOrderBean.setExpired(expired);
        return statisticOrderBean;
    }

}
