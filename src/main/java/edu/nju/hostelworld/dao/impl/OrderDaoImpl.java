package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.OrderDao;
import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.util.OrderState;
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
 * Created by Sorumi on 17/2/7.
 */

@Repository
public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {

    public ResultMessage addOrder(BookOrder bookOrder) {
        return add(bookOrder);
    }

    public ResultMessage updateOrder(BookOrder bookOrder) {
        return update(bookOrder);
    }

    public long countOrdersByBookedDate(String bookedDate) {
        long count = 0;
        try {
            Session session = setUpSession();
            String hql = "select count(*) from BookOrder where id >= :minID and id <= :maxID";
            Query query = session.createQuery(hql);
            query.setParameter("minID", bookedDate + "0000");
            query.setParameter("maxID", bookedDate + "9999");
            count = (long) query.list().get(0);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    public BookOrder findOrderByID(String ID) {
        return (BookOrder) findByID(BookOrder.class, ID);
    }

    public List<BookOrder> findOrderByOrderState(OrderState orderState) {
        if (orderState == null) {
            return findAllOrders();
        }
        return findByFieldAndValue(BookOrder.class, "state", orderState);
    }

    public List<BookOrder> findAllOrders() {
        return findAll(BookOrder.class);
    }

    public List<BookOrder> findMemberOrders(String memberID, OrderState orderState) {
        if (orderState == null) {
            return findByFieldAndValue(BookOrder.class, "memberID", memberID);
        }
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from BookOrder where memberID = :memberID and state = :state";
            Query query = session.createQuery(hql);
            query.setParameter("memberID", memberID);
            query.setParameter("state", orderState);
            list = query.list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<BookOrder> findHostelOrders(String hostelID, OrderState orderState) {
        if (orderState == null) {
            return findByFieldAndValue(BookOrder.class, "hostelID", hostelID);
        }
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from BookOrder where hostelID = :hostelID and state = :state";
            Query query = session.createQuery(hql);
            query.setParameter("hostelID", hostelID);
            query.setParameter("state", orderState);
            list = query.list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    public long countOrdersByStateAndDate(OrderState orderState, String dateField, String date) {
        long count = 0;
        try {
            Session session = setUpSession();
            String hql;
            Query query;
            if (orderState == null) {
                hql = "select count(*) from BookOrder where " + dateField + " like CONCAT(:date,'%')";
                query = session.createQuery(hql);
                query.setParameter("date", date);
            } else {
                hql = "select count(*) from BookOrder where state = :state and " + dateField + " like CONCAT(:date,'%')";
                query = session.createQuery(hql);
                query.setParameter("state", orderState);
                query.setParameter("date", date);
            }
            count = (Long) query.uniqueResult();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    public long countMemberOrdersByStateAndDate(String memberID, OrderState orderState, String dateField, String date) {
        long count = 0;
        try {
            Session session = setUpSession();
            String hql;
            Query query;
            if (orderState == null) {
                hql = "select count(*) from BookOrder where memberID = :memberID and " + dateField + " like CONCAT(:date,'%')";
                query = session.createQuery(hql);
                query.setParameter("memberID", memberID);
                query.setParameter("date", date);
            } else {
                hql = "select count(*) from BookOrder where memberID = :memberID and state = :state and " + dateField + " like CONCAT(:date,'%')";
                query = session.createQuery(hql);
                query.setParameter("memberID", memberID);
                query.setParameter("state", orderState);
                query.setParameter("date", date);
            }
            count = (Long) query.uniqueResult();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    public long countHostelOrdersByStateAndDate(String hostelID, OrderState orderState, String dateField, String date) {
        long count = 0;
        try {
            Session session = setUpSession();
            String hql;
            Query query;
            if (orderState == null) {
                hql = "select count(*) from BookOrder where hostelID = :hostelID and " + dateField + " like CONCAT(:date,'%')";
                query = session.createQuery(hql);
                query.setParameter("hostelID", hostelID);
                query.setParameter("date", date);
            } else {
                hql = "select count(*) from BookOrder where hostelID = :hostelID and state = :state and " + dateField + " like CONCAT(:date,'%')";
                query = session.createQuery(hql);
                query.setParameter("hostelID", hostelID);
                query.setParameter("state", orderState);
                query.setParameter("date", date);
            }
            count = (Long) query.uniqueResult();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

}
