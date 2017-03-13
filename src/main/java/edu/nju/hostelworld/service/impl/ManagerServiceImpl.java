package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.dao.ManagerDao;
import edu.nju.hostelworld.model.Manager;
import edu.nju.hostelworld.service.ManagerService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sorumi on 17/2/11.
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerDao;

    public ResultMessage addManager(Manager manager) {
        manager.setID(generateManagerID());
        return managerDao.addManager(manager);
    }

    @Override
    public ResultMessage updateManager(Manager manager) {
        return managerDao.updateManager(manager);
    }

    @Override
    public Manager findManagerByID(String ID) {
        return managerDao.findManagerByID(ID);
    }

    @Override
    public Manager findManagerByUsername(String username) {
        return managerDao.findManagerByUsername(username);
    }

    @Override
    public ResultMessage checkManager(String username, String password) {
        Manager checkManager = findManagerByUsername(username);

        if (checkManager == null) {
            return ResultMessage.NOT_EXIST;
        }
        if (password.equals(checkManager.getPassword())) {
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAILED;
    }

    @Override
    public List<Manager> findAllManagers() {
        return managerDao.findAllManagers();
    }

    private String generateManagerID() {
        int count = Math.toIntExact(managerDao.countManagers());
        return String.format("%07d", count);
    }
}
