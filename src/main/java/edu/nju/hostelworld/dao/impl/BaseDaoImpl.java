package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.BaseDao;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.util.ResultMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sorumi on 17/2/24.
 */
@Repository
public class BaseDaoImpl implements BaseDao {

    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * 初始化Session
     */
    public Session setUpSession() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    /**
     * 提交事务及关闭session
     */
    public void commitAndClose(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    protected ResultMessage add(Object object) {
        try {
            Session session = setUpSession();
            session.save(object);
            commitAndClose(session);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    protected ResultMessage update(Object object) {
        try {
            Session session = setUpSession();
            session.update(object);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    protected ResultMessage delete(Object object) {
        try {
            Session session = setUpSession();
            session.delete(object);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    protected ResultMessage delete(Class c, String ID) {
        try {
            Session session = setUpSession();
            Object object = session.get(c, ID);
            session.delete(object);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    protected long count(Class c) {
        long count = 0;
        try {
            Session session = setUpSession();
            String hql = "select count(*) from " + c.getName();
            count = (Long) session.createQuery(hql).uniqueResult();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    protected Object findByID(Class c, String ID) {
        Object object = null;
        try {
            Session session = setUpSession();
            object = session.get(c, ID);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    protected List findAll(Class c) {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from " + c.getName();
            list = session.createQuery(hql).list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    protected List findByFieldAndValue(Class c, String field, Object value) {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from " + c.getName() + " where " + field + " = :value";
            Query query = session.createQuery(hql);
            query.setParameter("value", value);
            list = query.list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

//    public Session getSession() {
//        return sessionFactory.getCurrentSession();
//    }
//
//    public Session getNewSession() {
//        return sessionFactory.openSession();
//    }
//
//    public void flush() {
//        getSession().flush();
//    }
//
//    public void clear() {
//        getSession().clear();
//    }

}
