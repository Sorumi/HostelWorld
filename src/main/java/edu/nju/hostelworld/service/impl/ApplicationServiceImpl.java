package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.bean.ApplicationBean;
import edu.nju.hostelworld.bean.OrderBean;
import edu.nju.hostelworld.dao.ApplicationDao;
import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.model.Application;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.service.ApplicationService;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sorumi on 17/2/28.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private HostelService hostelService;

    @Autowired
    ServletContext servletContext;

    @Override
    public ApplicationBean convertToApplicationBean(String ID) {
        ApplicationBean applicationBean = new ApplicationBean();
        Application application = findApplicationByID(ID);
        applicationBean.setApplication(application);
        applicationBean.setHostel(hostelService.findHostelByID(application.getHostelID()));
        return applicationBean;
    }

    @Override
    public String addApplication(Application application) {
        String ID = generateApplicationID();
        application.setID(ID);
        application.setState(ApplicationState.Unchecked);
        application.setAppliedTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
        ResultMessage resultMessage = applicationDao.addApplication(application);

        if (resultMessage == ResultMessage.SUCCESS) {
            return ID;
        }
        return null;
    }

    @Override
    public ResultMessage updateApplication(Application application) {
        return applicationDao.updateApplication(application);
    }

    @Override
    public ResultMessage deleteApplication(String ID) {
        return applicationDao.deleteApplication(ID);
    }

    @Override
    public ResultMessage passApplication(String ID) {
        Application application = applicationDao.findApplicationByID(ID);
        application.setState(ApplicationState.Passed);
        application.setCheckedTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
        ResultMessage resultMessage = applicationDao.updateApplication(application);

        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }

        Hostel hostel = hostelService.findHostelByID(application.getHostelID());
        hostel.setName(application.getName());
        hostel.setCity(application.getCity());
        hostel.setAddress(application.getAddress());
        hostel.setIntroduction(application.getIntroduction());
        hostel.setFacility(application.getFacility());


        String pathRoot = servletContext.getRealPath("");
        if (application.getImageType() != null) {

            new File(pathRoot + "/static/images/hostel").mkdirs();

            String imageType = application.getImageType();
            hostel.setImageType(imageType);

            File source = new File(pathRoot + "/static/images/application/" + application.getID() + "." + imageType);
            File dest = new File(pathRoot + "/static/images/hostel/" + hostel.getID() + "." + imageType);
            try {
                FileUtils.copyFile(source, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            File dest = new File(pathRoot + "/static/images/hostel/" + hostel.getID() + "." + hostel.getImageType());
            if (dest.exists()) {
                dest.delete();
            }
            hostel.setImageType(null);
        }

        if (hostel.getState() == HostelState.Unopened) {
            hostel.setState(HostelState.Opening);
        }

        return hostelService.updateHostel(hostel);
    }

    @Override
    public ResultMessage failApplication(String ID) {
        Application application = applicationDao.findApplicationByID(ID);
        application.setState(ApplicationState.Failed);
        application.setCheckedTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
        return applicationDao.updateApplication(application);
    }

    @Override
    public Application findApplicationByID(String ID) {
        return applicationDao.findApplicationByID(ID);
    }

    @Override
    public List<Application> findAllApplications() {
        List<Application> applications = applicationDao.findAllApplications();
        applications.sort(new ApplicationDateComparator());
        return applications;
    }

    @Override
    public List<Application> findApplicationsByType(ApplicationType type) {
        List<Application> applications = applicationDao.findApplicationsByType(type);
        applications.sort(new ApplicationDateComparator());
        return applications;
    }

    @Override
    public List<Application> findApplicationsByHostelID(String hostelID, ApplicationType type) {
        List<Application> applications = applicationDao.findApplicationsByHostelID(hostelID, type);
        applications.sort(new ApplicationDateComparator());
        return applications;
    }

    private String generateApplicationID() {
        int count = Math.toIntExact(applicationDao.countApplications());
        return String.format("%07d", count);
    }

    private class ApplicationDateComparator implements Comparator<Application> {
        public int compare(Application a1, Application a2) {
            return -a1.getAppliedTime().compareTo(a2.getAppliedTime());
        }
    }
}
