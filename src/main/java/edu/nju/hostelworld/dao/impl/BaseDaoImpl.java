package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
