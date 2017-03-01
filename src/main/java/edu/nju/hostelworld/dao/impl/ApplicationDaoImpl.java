package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.ApplicationDao;
import edu.nju.hostelworld.model.Application;
import edu.nju.hostelworld.util.ApplicationType;
import edu.nju.hostelworld.util.ResultMessage;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sorumi on 17/2/28.
 */
@Repository
public class ApplicationDaoImpl extends BaseDaoImpl implements ApplicationDao {
    @Override
    public ResultMessage addApplication(Application application) {
        return add(application);
    }

    @Override
    public ResultMessage updateApplication(Application application) {
        return update(application);
    }

    @Override
    public ResultMessage deleteApplication(String ID) {
        return delete(Application.class, ID);
    }

    @Override
    public long countApplications() {
        return count(Application.class);
    }

    @Override
    public Application findApplicationByID(String ID) {
        return (Application) findByID(Application.class, ID);
    }

    @Override
    public List<Application> findAllApplications() {
        return findAll(Application.class);
    }

    @Override
    public List<Application> findApplicationsByType(ApplicationType type) {
        if (type == null) {
            return findAllApplications();
        }
        return findByFieldAndValue(Application.class, "type", type);
    }

    @Override
    public List<Application> findApplicationsByHostelID(String hostelID, ApplicationType type) {
        if (type == null) {
            return findByFieldAndValue(Application.class, "hostelID", hostelID);
        }
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from Application where hostelID = :hostelID and type = :type";
            Query query = session.createQuery(hql);
            query.setParameter("hostelID", hostelID);
            query.setParameter("type", type);
            list = query.list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
