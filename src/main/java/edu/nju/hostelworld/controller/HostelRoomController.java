package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.AlertBean;
import edu.nju.hostelworld.bean.HostelRoomListBean;
import edu.nju.hostelworld.bean.HostelSearchRoomBean;
import edu.nju.hostelworld.bean.SearchRoomJsonBean;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.HostelRoom;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.util.HostelState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Sorumi on 17/2/26.
 */

@Controller
@RequestMapping("/hostel")
@SessionAttributes({"hostel"})
public class HostelRoomController {

    @Autowired
    private HostelService hostelService;

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public String homeGet(ModelMap model) {

        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() == HostelState.Unopened) {
            return "redirect:/hostel/home";
        }

        HostelSearchRoomBean hostelSearchRoomBean = new HostelSearchRoomBean();
        hostelSearchRoomBean.setDate(LocalDate.now().toString());

        List roomStocks = hostelService.getRoomStockByDate(hostel.getID(),
                LocalDate.parse(hostelSearchRoomBean.getDate()));
        hostelSearchRoomBean.setRooms(roomStocks);

        model.addAttribute("hostelSearchRoomBean", hostelSearchRoomBean);
        return "hostel-room";
    }

    @RequestMapping(value = "/{id}/roomstock", method = RequestMethod.POST)
    @ResponseBody
    public List hostelRoomStocks(@RequestBody SearchRoomJsonBean searchRoomJsonBean) {
        List roomStocks = hostelService.getRoomStockByDate(searchRoomJsonBean.getHostelID(),
                LocalDate.parse(searchRoomJsonBean.getDate()));
        return roomStocks;
    }

    @RequestMapping(value = "/plan", method = RequestMethod.GET)
    public String planGet(ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() == HostelState.Unopened) {
            return "redirect:/hostel/home";
        }

        HostelRoomListBean hostelRoomListBean = new HostelRoomListBean();
        List hostelRooms = hostelService.findHostelRoomsByHostelID(hostel.getID());
        hostelRoomListBean.setHostelRooms(hostelRooms);

        model.addAttribute("hostelRoomListBean", hostelRoomListBean);

        return "hostel-plan-list";
    }

    @RequestMapping(value = "/plan/add", method = RequestMethod.GET)
    public String planAddGet(ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() == HostelState.Unopened) {
            return "redirect:/hostel/home";
        }

//        HostelRoom hostelRoom = new HostelRoom();
//        hostelRoom.setStartDate(LocalDate.now().toString());
//        hostelRoom.setEndDate(LocalDate.now().plusMonths(1).toString());

//        model.addAttribute("hostelRoom", hostelRoom);

        return "hostel-plan-edit";
    }

    @RequestMapping(value = "/plan/add", method = RequestMethod.POST)
    public String planAddPost(HostelRoom hostelRoom, ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() == HostelState.Unopened) {
            return "redirect:/hostel/home";
        }

        String name = hostelRoom.getName();
        String startDate = hostelRoom.getStartDate();
        String endDate = hostelRoom.getEndDate();

        if (!isValid(name) || !isValid(startDate) || !isValid(endDate)) {
            model.addAttribute("alert", "请将信息填写完整！");
            model.addAttribute("hostelRoom", hostelRoom);
            return "hostel-plan-edit";
        }

        hostelRoom.setHostelID(hostel.getID());

        ResultMessage resultMessage = hostelService.addHostelRoom(hostelRoom);
        if (resultMessage == ResultMessage.SUCCESS) {
            AlertBean alertBean = new AlertBean();

            alertBean.setMessage("添加成功！");
            alertBean.setUrl("hostel/plan");
            alertBean.setButton("查看");
            model.addAttribute("alertBean", alertBean);

            return "alert-href";
        } else {
            model.addAttribute("alertMessage", "添加失败！");
        }
        return "alert";
    }

    private boolean isValid(String keyword) {
        return keyword != null && !keyword.equals("");
    }

}
