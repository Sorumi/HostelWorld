package edu.nju.hostelworld.service;

import edu.nju.hostelworld.bean.ApplicationBean;
import edu.nju.hostelworld.model.Application;
import edu.nju.hostelworld.util.ApplicationType;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/28.
 */
public interface ApplicationService {

    public ApplicationBean convertToApplicationBean(String ID);

    public String addApplication(Application application);

    public ResultMessage updateApplication(Application application);

    public ResultMessage deleteApplication(String ID);

    public ResultMessage passApplication(String ID);

    public ResultMessage failApplication(String ID);

    public Application findApplicationByID(String ID);

    public List<Application> findAllApplications();

    public List<Application> findApplicationsByType(ApplicationType type);

    public List<Application> findApplicationsByHostelID(String hostelID, ApplicationType type);

}
