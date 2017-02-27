package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.Manager;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/11.
 */
public interface ManagerService {


    public ResultMessage addManager(Manager manager);

    public ResultMessage updateManager(Manager manager);

    public Manager findManagerByID(String ID);

    public Manager findManagerByUsername(String username);

    public ResultMessage checkManager(String username, String password);

    public List<Manager> findAllManagers();

}
