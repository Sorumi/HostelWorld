package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.*;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.HostelRoom;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.util.HostelState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
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
    ServletContext servletContext;

    @Autowired
    private HostelService hostelService;

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public String room(ModelMap model) {

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

        model.addAttribute("title", "房间管理");
        model.addAttribute("nav", "nav-room");
        return "hostel-room";
    }

    @RequestMapping(value = "/{id}/roomstock", method = RequestMethod.POST)
    @ResponseBody
    public List hostelRoomStocks(@RequestBody SearchRoomJsonBean searchRoomJsonBean) {
        List<RoomStockBean> roomStocks = hostelService.getRoomStockByDate(searchRoomJsonBean.getHostelID(),
                LocalDate.parse(searchRoomJsonBean.getDate()));
        return roomStocks;
    }

    @RequestMapping(value = "/plan", method = RequestMethod.GET)
    public String plan(ModelMap model) {
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

        model.addAttribute("title", "房间计划");
        model.addAttribute("nav", "nav-room");

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

        model.addAttribute("title", "发布房间计划");
        model.addAttribute("nav", "nav-room");

        return "hostel-plan-edit";
    }

    @RequestMapping(value = "/plan/add", method = RequestMethod.POST)
    public String planAddPost(HostelRoom hostelRoom, @RequestParam(value = "image", required = false) MultipartFile image, ModelMap model) throws IOException {
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

        String hostelID = hostel.getID();
        hostelRoom.setHostelID(hostelID);

        String hostelRoomID = hostelService.addHostelRoom(hostelRoom);
        if (hostelRoomID != null) {

            // image
            String pathRoot = servletContext.getRealPath("");
            String path;

            if (image != null && !image.isEmpty()) {
                new File(pathRoot + "/static/images/hostelroom/" + hostelID + "/").mkdirs();

                String contentType = image.getContentType();
                String imageType = contentType.substring(contentType.indexOf("/") + 1);
                path = "/static/images/hostelroom/" + hostelID + "/" + hostelRoomID + "." + imageType;
                image.transferTo(new File(pathRoot + path));
                hostelRoom.setImageType(imageType);
            }

            hostelService.updateHostelRoom(hostelRoom);

            // alert
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
