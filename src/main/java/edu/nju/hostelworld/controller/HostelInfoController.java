package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.bean.AlertBean;
import edu.nju.hostelworld.bean.HostelInfoBean;
import edu.nju.hostelworld.model.Application;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.service.ApplicationService;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sorumi on 17/2/26.
 */

@Controller
@RequestMapping("/hostel")
@SessionAttributes({"hostel"})
public class HostelInfoController {

    @Autowired
    ServletContext servletContext;

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {

        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() != HostelState.Unopened) {
//            model.addAttribute("hostel", hostel);
            return "redirect:/hostel/info";
        }

        HostelInfoBean hostelInfoBean = new HostelInfoBean();
        hostelInfoBean.setHostel(hostel);
        List<Application> list = applicationService.findApplicationsByHostelID(hostel.getID(), ApplicationType.Open);
        if (list != null && list.size() > 0) {
            hostelInfoBean.setOpenApplication(list.get(list.size() - 1));
        }
        model.addAttribute("hostelInfoBean", hostelInfoBean);

        return "hostel-home";
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() == HostelState.Unopened) {
            return "redirect:/hostel/home";
        }

//        model.addAttribute("hostel", hostel);
        return "hostel-info";
    }

    @RequestMapping(value = "/application", method = RequestMethod.GET)
    public String applicationList(@RequestParam(value = "type", required = false) String type, ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");

        ApplicationType applicationType = null;

        if (type != null) {
            applicationType = ApplicationType.getByString(type);
        }
        List<Application> applicationList = applicationService.findApplicationsByHostelID(hostel.getID(), applicationType);
        model.addAttribute("applicationList", applicationList);
        model.addAttribute("applicationType", applicationType);

        return "hostel-application-list";
    }

    @RequestMapping(value = "/application/{id}", method = RequestMethod.GET)
    public String application(@PathVariable(value = "id") String ID, ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");

        Application application = applicationService.findApplicationByID(ID);
        if (application == null) {
            model.addAttribute("alertMessage", "申请不存在！");
            return "alert";
        } else if (!application.getHostelID().equals(hostel.getID())) {
            model.addAttribute("alertMessage", "您无法查看此申请！");
            return "alert";
        }

        model.addAttribute("application", application);

        return "hostel-application-info";
    }

    @RequestMapping(value = "/application/open/add", method = RequestMethod.GET)
    public String applicationOpenAddGet(ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        List<Application> applicationList = applicationService.findApplicationsByHostelID(hostel.getID(), ApplicationType.Open);

        if (hostel.getState() != HostelState.Unopened) {
            return "redirect:/hostel/home";
        }

        if (applicationList.size() > 0) {
            Application application = applicationList.get(applicationList.size() - 1);
            if (application.getState() != ApplicationState.Failed) {
                return "redirect:/hostel/home";
            }
        }

        Application application = new Application();
        application.setType(ApplicationType.Open);
        application.setHostelID(hostel.getID());

        model.addAttribute("application", application);
        model.addAttribute("cities", City.values());

        return "hostel-application-edit";
    }

    @RequestMapping(value = "/application/open/add", method = RequestMethod.POST)
    public String applicationOpenAddPost(Application application, @RequestParam(value = "image", required = false) MultipartFile image, ModelMap model) throws IOException {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        List<Application> applicationList = applicationService.findApplicationsByHostelID(hostel.getID(), ApplicationType.Open);

        if (hostel.getState() != HostelState.Unopened) {
            return "redirect:/hostel/home";
        }

        if (applicationList.size() > 0) {
            Application a = applicationList.get(applicationList.size() - 1);
            if (a.getState() != ApplicationState.Failed) {
                return "redirect:/hostel/home";
            }
        }

        application.setType(ApplicationType.Open);
        application.setHostelID(hostel.getID());

        String applicationID = applicationService.addApplication(application);

        if (applicationID != null) {

            // image
            String pathRoot = servletContext.getRealPath("");
            String path;

            if (image != null && !image.isEmpty()) {
                new File(pathRoot + "/static/images/application").mkdirs();

                String name = applicationID;
                String contentType = image.getContentType();
                String imageType = contentType.substring(contentType.indexOf("/") + 1);
                path = "/static/images/application/" + name + "." + imageType;
                image.transferTo(new File(pathRoot + path));
                application.setImageType(imageType);
            }

            applicationService.updateApplication(application);

            // alert
            AlertBean alertBean = new AlertBean();

            alertBean.setMessage("申请成功！");
            alertBean.setUrl("hostel/application/" + applicationID);
            alertBean.setButton("查看");
            model.addAttribute("alertBean", alertBean);

            return "alert-href";
        } else {
            model.addAttribute("alertMessage", "申请失败！");
        }

        return "alert";
    }


    @RequestMapping(value = "/application/edit/add", method = RequestMethod.GET)
    public String applicationEditAddGet(ModelMap model) {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() != HostelState.Opening) {
            return "redirect:/hostel/home";
        }

        Application application = new Application();
        application.setType(ApplicationType.Edit);
        application.setHostelID(hostel.getID());
        application.setName(hostel.getName());
        application.setAddress(hostel.getAddress());
        application.setIntroduction(hostel.getIntroduction());
        application.setFacility(hostel.getFacility());

        model.addAttribute("application", application);
        model.addAttribute("cities", City.values());

        return "hostel-application-edit";
    }

    @RequestMapping(value = "/application/edit/add", method = RequestMethod.POST)
    public String applicationEditAddPost(Application application, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam(value = "imageChanged", required = false) int change, ModelMap model) throws IOException {
        boolean isLogin = model.containsAttribute("hostel");
        if (!isLogin) {
            return "redirect:/hostel/login";
        }
        Hostel hostel = (Hostel) model.get("hostel");
        if (hostel.getState() != HostelState.Opening) {
            return "redirect:/hostel/home";
        }

        application.setType(ApplicationType.Edit);
        application.setHostelID(hostel.getID());

        String applicationID = applicationService.addApplication(application);


        if (applicationID != null) {
            // image
            String pathRoot = servletContext.getRealPath("");
            String path;

            new File(pathRoot + "/static/images/application").mkdirs();

            if (image != null && !image.isEmpty()) {

                String name = applicationID;
                String contentType = image.getContentType();
                String imageType = contentType.substring(contentType.indexOf("/") + 1);
                path = "/static/images/application/" + name + "." + imageType;
                image.transferTo(new File(pathRoot + path));
                application.setImageType(imageType);

            } else if (change == 0 && hostel.getImageType() != null) {

                String imageType = hostel.getImageType();
                hostel.setImageType(imageType);

                File source = new File(pathRoot + "/static/images/hostel/" + hostel.getID() + "." + imageType);
                File dest = new File(pathRoot + "/static/images/application/" + application.getID() + "." + imageType);

                try {
                    FileUtils.copyFile(source, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            applicationService.updateApplication(application);

            // alert
            AlertBean alertBean = new AlertBean();

            alertBean.setMessage("申请成功！");
            alertBean.setUrl("hostel/application/" + applicationID);
            alertBean.setButton("查看");
            model.addAttribute("alertBean", alertBean);

            return "alert-href";
        } else {
            model.addAttribute("alertMessage", "申请失败！");
        }


        return "alert";
    }
}
