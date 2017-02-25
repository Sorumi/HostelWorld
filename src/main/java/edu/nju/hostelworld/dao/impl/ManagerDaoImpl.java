package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.ManagerDao;
import edu.nju.hostelworld.model.Manager;
import edu.nju.hostelworld.util.ResultMessage;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sorumi on 17/2/2.
 */

@Repository
public class ManagerDaoImpl extends BaseDaoImpl implements ManagerDao {

    public ResultMessage addManager(Manager manager) {
        return add(manager);
    }

    public ResultMessage updateManager(Manager manager) {
        return update(manager);
    }

    public long countManager() {
        return count(Manager.class);
    }

    public Manager findManagerByID(String ID) {
        return (Manager) findByID(Manager.class, ID);
    }

    public Manager findManagerByUsername(String username) {
        Manager manager = null;
        try {
            Session session = setUpSession();
            String hql = "from Manager where username = " + username;
            List list = session.createQuery(hql).list();
            manager = list.size() == 0 ? null : (Manager) list.get(0);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return manager;
    }

    public List<Manager> findAllManagers() {
        return findAll(Manager.class);
    }
}
