package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.Bean.*;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.service.MemberService;
import edu.nju.hostelworld.service.OrderService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Sorumi on 17/2/25.
 */

@Controller
@RequestMapping("/")
@SessionAttributes({"member", "memberHostelInfoBean", "orderBean"})
public class MemberSearchHostelController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private HostelService hostelService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {
        SearchHostelBean searchHostelBean = new SearchHostelBean();
        searchHostelBean.setCheckInDate(LocalDate.now().toString());
        searchHostelBean.setCheckOutDate(LocalDate.now().plusDays(1).toString());
        model.addAttribute("searchHostelBean", searchHostelBean);
        return "member-home";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchHostel(SearchHostelBean searchHostelBean, ModelMap model) {

        List hostels = hostelService.findHostelsByKeyword(searchHostelBean.getKeyword());
//        System.out.println(hostels.size());
        searchHostelBean.setHostels(hostels);

        return "member-search-hostel";
    }

    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    public String hostel(@PathVariable("id") String id, ModelMap model) {
        MemberHostelInfoBean memberHostelInfoBean = new MemberHostelInfoBean();
        memberHostelInfoBean.setCheckInDate(LocalDate.now().minusDays(15).toString());
        memberHostelInfoBean.setCheckOutDate(LocalDate.now().minusDays(14).toString());

        memberHostelInfoBean.setHostel(hostelService.findHostelByID(id));
        List list = hostelService.getRoomStockByCheckDate(id,
                LocalDate.parse(memberHostelInfoBean.getCheckInDate()),
                LocalDate.parse(memberHostelInfoBean.getCheckOutDate()));

        memberHostelInfoBean.setRoomStocks(list);
        model.addAttribute("memberHostelInfoBean", memberHostelInfoBean);

        return "member-hostel";
    }

    @RequestMapping(value = "/search/{id}/roomstock", method = RequestMethod.POST)
    @ResponseBody
    public List hostelRoomStocks(@RequestBody SearchRoomJsonBean searchRoomJsonBean) {
        List list = hostelService.getRoomStockByCheckDate(searchRoomJsonBean.getHostelID(),
                LocalDate.parse(searchRoomJsonBean.getCheckInDate()),
                LocalDate.parse(searchRoomJsonBean.getCheckOutDate()));
       return list;
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public String book(MemberHostelInfoBean memberHostelInfoBean, HttpSession session, ModelMap model) {
        Member member;
        if (model.get("member") == null) {
            return "redirect:/login";
        } else {
            member = (Member)model.get("member");
        }

        if (memberHostelInfoBean.getBookQuantity() == null || memberHostelInfoBean.getBookQuantity().size() == 0) {
            return "redirect:/home";
        }

        if (session.getAttribute("memberHostelInfoBean") == null) {
            return "redirect:/home";
        }
        MemberHostelInfoBean oldBean = (MemberHostelInfoBean) session.getAttribute("memberHostelInfoBean");
//        System.out.println(memberHostelInfoBean.getBookQuantity().size());
        if (!memberHostelInfoBean.getHostel().getID().equals(oldBean.getHostel().getID())) {
            return "member-home";
        }
        oldBean.setBookQuantity(memberHostelInfoBean.getBookQuantity());
        oldBean.setCheckInDate(memberHostelInfoBean.getCheckInDate());
        oldBean.setCheckOutDate(memberHostelInfoBean.getCheckOutDate());

        OrderBean orderBean = orderService.generateOrder(oldBean, member);
        model.addAttribute("orderBean", orderBean);
//        session.removeAttribute("memberHostelInfoBean");

        return "member-book";
    }

    @RequestMapping(value = "/book/submit", method = RequestMethod.POST)
    public String bookSubmit(OrderBean orderBean, HttpSession session, ModelMap model) {

        if (model.get("member") == null) {
            return "redirect:/login";
        }

        if (orderBean.getBookOrder().getPeopleQuantity() <= 0) {
            return "redirect:/home";
        }

        if (session.getAttribute("orderBean") == null) {
            return "redirect:/home";
        }
        OrderBean oldBean = (OrderBean) session.getAttribute("orderBean");
        oldBean.getBookOrder().setPeopleQuantity(orderBean.getBookOrder().getPeopleQuantity());

        ResultMessage resultMessage = orderService.addNewOrder(oldBean);

        if (resultMessage == ResultMessage.SUCCESS) {
            session.removeAttribute("memberHostelInfoBean");
            session.removeAttribute("orderBean");

            model.addAttribute("alertMessage", "预订成功！");
        } else {
            model.addAttribute("alertMessage", "预订失败！");
        }

        return "alert";
    }

}
