package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.HostelDao;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.util.HostelState;
import edu.nju.hostelworld.util.ResultMessage;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sorumi on 17/2/5.
 */

@Repository
public class HostelDaoImpl extends BaseDaoImpl implements HostelDao {

    public ResultMessage addHostel(Hostel hostel) {
        return add(hostel);
    }

    public ResultMessage updateHostel(Hostel hostel) {
        return updateHostel(hostel);
    }

    public long countHostels() {
        return count(Hostel.class);
    }

    public Hostel findHostelByID(String ID) {
        return (Hostel) findByID(Hostel.class, ID);
    }

    public Hostel findHostelByUsername(String username) {
        Hostel hostel = null;
        try {
            Session session = setUpSession();
            String hql = "from Hostel where username = :username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            List list = query.list();
            hostel = list.size() == 0 ? null : (Hostel) list.get(0);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return hostel;
    }

    public List<Hostel> findAllHostels() {
        return findAll(Hostel.class);
    }

    public List<Hostel> findHostelsByState(HostelState state) {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from Hostel where state = " + state;
            list = session.createQuery(hql).list();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Hostel> findHostelByKeyword(String keyword, String value) {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from Hostel where " + keyword + " like :value and state = :state";
            Query query = session.createQuery(hql);
            query.setParameter("value", "%" + value + "%");
            query.setParameter("state", HostelState.Opening);
            list = query.list();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
