package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.FinanceRecordBean;
import edu.nju.hostelworld.bean.StatisticOrderBean;
import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.*;
import edu.nju.hostelworld.util.HostelState;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Created by Sorumi on 17/3/11.
 */

@Controller
@RequestMapping("/admin")
@SessionAttributes({"manager"})
public class ManagerStatisticController {

    @Autowired
    private FinanceRecordService financeRecordService;

    @Autowired
    private AppService appService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private HostelService hostelService;

    @Autowired
    private MemberService memberService;

    @ResponseBody
    @RequestMapping(value = "/commission", method = RequestMethod.POST)
    public String commissionPost(@RequestParam double commission, ModelMap model) {
        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        System.out.print(commission);

        ResultMessage resultMessage = appService.updateCommission(commission);
        return resultMessage.toString();
    }

    @RequestMapping(value = "/statistic/money", method = RequestMethod.GET)
    public String statisticMoney(ModelMap model) {

        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        App app = appService.findApp();
        model.addAttribute("app", app);

        List<FinanceRecordBean> financeRecords = financeRecordService.findFinanceManagerRecords();
        model.addAttribute("financeRecords", financeRecords);

        model.addAttribute("title", "财务统计");
        model.addAttribute("nav", "nav-money");

        return "manager-statistic-money";
    }

    @RequestMapping(value = "/statistic/hostel", method = RequestMethod.GET)
    public String statisticHostel(ModelMap model) {

        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        List<Hostel> hostels = hostelService.findAllHostels();
        model.addAttribute("hostels", hostels);

        model.addAttribute("title", "旅舍统计");
        model.addAttribute("nav", "nav-hostel");

        return "manager-statistic-hostel";
    }

    @RequestMapping(value = "/statistic/hostel/{id}/{month}", method = RequestMethod.GET)
    @ResponseBody
    public StatisticOrderBean statisticHostelMonth(@PathVariable("id") String hostelID, @PathVariable("month") String month, ModelMap model) {

        if (model.get("manager") == null) {
            return null;
        }

        LocalDate date = LocalDate.parse(month + "-01");

        ZoneId zoneId = ZoneId.systemDefault();
        long time = date.atStartOfDay(zoneId).toEpochSecond() * 1000;

        List<Integer> booked = orderService.countHostelOrdersByStateAndMonth(hostelID, null, month);
        List<Integer> checkIn = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.CheckIn, month);
        List<Integer> checkOut = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.CheckOut, month);
        List<Integer> cancelled = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.Cancelled, month);
        List<Integer> expired = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.Expired, month);
        StatisticOrderBean statisticOrderBean = new StatisticOrderBean();
        statisticOrderBean.setTime(time);
        statisticOrderBean.setBooked(booked);
        statisticOrderBean.setCheckIn(checkIn);
        statisticOrderBean.setCheckOut(checkOut);
        statisticOrderBean.setCancelled(cancelled);
        statisticOrderBean.setExpired(expired);
        return statisticOrderBean;
    }

    @RequestMapping(value = "/statistic/member", method = RequestMethod.GET)
    public String statisticMember(ModelMap model) {

        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        List<Member> members = memberService.findAllMembers();
        model.addAttribute("members", members);

        model.addAttribute("title", "会员统计");
        model.addAttribute("nav", "nav-member");

        return "manager-statistic-member";
    }

    @RequestMapping(value = "/statistic/member/{id}/{year}", method = RequestMethod.GET)
    @ResponseBody
    public StatisticOrderBean statisticMemberYear(@PathVariable("id") String memberID, @PathVariable("year") String year, ModelMap model) {

        if (model.get("manager") == null) {
            return null;
        }

        LocalDate date = LocalDate.parse(year + "-01-01");

        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        long time = date.atStartOfDay(zoneId).toEpochSecond() * 1000;

        List<Integer> booked = orderService.countMemberOrdersByStateAndYear(memberID, null, year);
        List<Integer> checkIn = orderService.countMemberOrdersByStateAndYear(memberID, OrderState.CheckIn, year);
        List<Integer> checkOut = orderService.countMemberOrdersByStateAndYear(memberID, OrderState.CheckOut, year);
        List<Integer> cancelled = orderService.countMemberOrdersByStateAndYear(memberID, OrderState.Cancelled, year);
        List<Integer> expired = orderService.countMemberOrdersByStateAndYear(memberID, OrderState.Expired, year);
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
