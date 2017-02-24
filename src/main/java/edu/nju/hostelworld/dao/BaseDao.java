package edu.nju.hostelworld.dao;

import org.hibernate.Session;

/**
 * Created by Sorumi on 17/2/24.
 */
public interface BaseDao {
//    public Session getSession();
//
//    public Session getNewSession();
//
//    public void flush();
//
//    public void clear() ;

    public Session setUpSession();

    public void commitAndClose(Session session);
}
