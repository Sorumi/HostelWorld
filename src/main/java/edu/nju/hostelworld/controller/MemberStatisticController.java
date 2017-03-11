package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.FinanceRecordBean;
import edu.nju.hostelworld.bean.StatisticOrderBean;
import edu.nju.hostelworld.model.FinanceRecord;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.FinanceRecordService;
import edu.nju.hostelworld.service.OrderService;
import edu.nju.hostelworld.util.HostelState;
import edu.nju.hostelworld.util.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Created by Sorumi on 17/3/7.
 */

@Controller
@RequestMapping("/")
@SessionAttributes({"member"})
public class MemberStatisticController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FinanceRecordService financeRecordService;

    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public String statisticGet(ModelMap model) {

        if (model.get("member") == null) {
            return "redirect:/login";
        }
        Member member = (Member) model.get("member");

        List<FinanceRecordBean> financeRecords = financeRecordService.findFinanceMemberRecords(member.getID());
        model.addAttribute("financeRecords", financeRecords);

        return "member-statistic";
    }

    @RequestMapping(value = "/statistic/{year}", method = RequestMethod.GET)
    @ResponseBody
    public StatisticOrderBean statisticYear(@PathVariable String year, ModelMap model) {

        if (model.get("member") == null) {
            return null;
        }
        Member member = (Member) model.get("member");

        LocalDate date = LocalDate.parse(year + "-01-01");

        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        long time = date.atStartOfDay(zoneId).toEpochSecond() * 1000;

        List<Integer> booked = orderService.countMemberOrdersByStateAndYear(member.getID(), null, year);
        List<Integer> checkIn = orderService.countMemberOrdersByStateAndYear(member.getID(), OrderState.CheckIn, year);
        List<Integer> checkOut = orderService.countMemberOrdersByStateAndYear(member.getID(), OrderState.CheckOut, year);
        List<Integer> cancelled = orderService.countMemberOrdersByStateAndYear(member.getID(), OrderState.Cancelled, year);
        List<Integer> expired = orderService.countMemberOrdersByStateAndYear(member.getID(), OrderState.Expired, year);
        StatisticOrderBean statisticOrderBean = new StatisticOrderBean();
        statisticOrderBean.setTime(time);
        statisticOrderBean.setBooked(booked);
        statisticOrderBean.setCheckIn(checkIn);
        statisticOrderBean.setCheckOut(checkOut);
        statisticOrderBean.setCancelled(cancelled);
        statisticOrderBean.setExpired(expired);
        return statisticOrderBean;
    }

}
