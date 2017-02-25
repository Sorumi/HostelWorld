package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.HostelRoomDao;
import edu.nju.hostelworld.model.HostelRoom;
import edu.nju.hostelworld.model.RoomStock;
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
public class HostelRoomDaoImpl extends BaseDaoImpl implements HostelRoomDao {

    public ResultMessage addHostelRoom(HostelRoom hostelRoom) {
        return add(hostelRoom);
    }

    @Transactional
    public ResultMessage updateHostelRoom(HostelRoom hostelRoom) {
        return update(hostelRoom);
    }

    @Transactional
    public ResultMessage deleteHostelRoom(HostelRoom hostelRoom) {
        return delete(hostelRoom);
    }

    public long countHostelRoomsByHostelID(String hostelID) {
        long count = 0;
        try {
            Session session = setUpSession();
            String hql = "select count(*) from HostelRoom where hostelID = " + hostelID;
            count = (Long) session.createQuery(hql).uniqueResult();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    public HostelRoom findHostelRoomByID(String ID) {
        return (HostelRoom) findByID(HostelRoom.class, ID);
    }

    public List<HostelRoom> findHostelRoomsByHostelID(String hostelID) {
        return findByFieldAndValue(HostelRoom.class, "hostelID", hostelID);
    }

//
//    public List<HostelRoom> findHostelRoomsByHostelIDAndDate(String hostelID, String date) {
//        List list = null;
//        try {
//            list = hibernateTemplateMysql.find("from HostelRoom where hostelID = ? and startDate <= ? and endDate >= ? ", hostelID, date, date);
//        } catch (DataAccessException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    public List<HostelRoom> findHostelRoomsByHostelIDAndDate(String hostelID, String startDate, String endDate) {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from HostelRoom where hostelID = :hostelID and startDate <= :startDate and endDate >= :endDate";
            Query query = session.createQuery(hql);
            query.setParameter("hostelID", hostelID);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            list = query.list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ResultMessage addRoomStock(RoomStock roomStock) {
        return add(roomStock);
    }

    public ResultMessage updateRoomStock(RoomStock roomStock) {
        return update(roomStock);
    }

    public ResultMessage deleteRoomStock(RoomStock roomStock) {
        return delete(roomStock);
    }

    public List<RoomStock> findRoomStocksByHostelRoomID(String hostelRoomID) {
        return findByFieldAndValue(RoomStock.class, "hostelRoomID", hostelRoomID);
    }

    public RoomStock findRoomStockByHostelRoomIDAndDate(String hostelRoomID, String date) {
        RoomStock roomStock = null;
        try {
            Session session = setUpSession();
            String hql = "from RoomStock where hostelRoomID = :hostelRoomID and date = :date";
            Query query = session.createQuery(hql);
            query.setParameter("hostelRoomID", hostelRoomID);
            query.setParameter("date", date);
            List list = query.list();
            roomStock = list.size() == 0 ? null : (RoomStock) list.get(0);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return roomStock;
    }

    public int minQuantityOfRoomStockByHostelRoomIDAndDate(String hostelRoomID, String startDate, String endDate) {
        int quantity = 0;
        try {
            Session session = setUpSession();
            String hql = "select min(quantity) from RoomStock where hostelRoomID = :hostelRoomID and date >= :startDate and date <= :endDate";
            Query query = session.createQuery(hql);
            query.setParameter("hostelRoomID", hostelRoomID);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            quantity = (int)query.list().get(0);
//            session.createQuery(hql).list().get(0);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return quantity;
    }
}
