package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.Bean.ApplicationBean;
import edu.nju.hostelworld.dao.ApplicationDao;
import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.model.Application;
import edu.nju.hostelworld.service.ApplicationService;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.util.ApplicationState;
import edu.nju.hostelworld.util.ApplicationType;
import edu.nju.hostelworld.util.DateAndTimeUtil;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public ApplicationBean convertToApplicationBean(String ID) {
        ApplicationBean applicationBean = new ApplicationBean();
        Application application = findApplicationByID(ID);
        applicationBean.setApplication(application);
        applicationBean.setHostel(hostelService.findHostelByID(application.getHostelID()));
        return applicationBean;
    }

    @Override
    public ResultMessage addApplication(Application application) {

        application.setID(generateApplicationID());
        application.setState(ApplicationState.Unchecked);
        application.setAppliedTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
        return applicationDao.addApplication(application);
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
        return applicationDao.updateApplication(application);
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
        return applicationDao.findAllApplications();
    }

    @Override
    public List<Application> findApplicationsByType(ApplicationType type) {
        return applicationDao.findApplicationsByType(type);
    }

    @Override
    public List<Application> findApplicationsByHostelID(String hostelID, ApplicationType type) {
        return applicationDao.findApplicationsByHostelID(hostelID, type);
    }

    private String generateApplicationID() {
        int count = Math.toIntExact(applicationDao.countApplications());
        return String.format("%07d", count);
    }
}
