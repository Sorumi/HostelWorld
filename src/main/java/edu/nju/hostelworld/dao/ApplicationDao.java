package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.Application;
import edu.nju.hostelworld.util.ApplicationType;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/28.
 */
public interface ApplicationDao {

    public ResultMessage addApplication(Application application);

    public ResultMessage updateApplication(Application application);

    public ResultMessage deleteApplication(String ID);

    public long countApplications();

    public Application findApplicationByID(String ID);

    public List<Application> findAllApplications();

    public List<Application> findApplicationsByType(ApplicationType type);

    public List<Application> findApplicationsByHostelID(String hostelID, ApplicationType type);

}
