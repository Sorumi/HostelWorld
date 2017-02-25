package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.Manager;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/2.
 */
public interface ManagerDao {

    public ResultMessage addManager(Manager manager);

    public ResultMessage updateManager(Manager manager);

    public long countManager();

    public Manager findManagerByID(String ID);

    public Manager findManagerByUsername(String username);

    public List<Manager> findAllManagers();
}
