package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.FinanceRecordDao;
import edu.nju.hostelworld.model.FinanceRecord;
import edu.nju.hostelworld.util.ResultMessage;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sorumi on 17/3/10.
 */
@Repository
public class FinanceRecordDaoImpl extends BaseDaoImpl implements FinanceRecordDao {
    @Override
    public ResultMessage addFinanceRecord(FinanceRecord financeRecord) {
        return add(financeRecord);
    }

    @Override
    public ResultMessage updateFinanceRecord(FinanceRecord financeRecord) {
        return update(financeRecord);
    }

    @Override
    public long countFinanceRecords() {
        return count(FinanceRecord.class);
    }

    @Override
    public FinanceRecord findFinanceRecordByID(String ID) {
        return (FinanceRecord) findByID(FinanceRecord.class, ID);
    }

    @Override
    public List<FinanceRecord> findFinanceMemberRecords(String memberID) {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "FROM FinanceRecord WHERE " +
                    "memberID = :memberID and (type = 'Cancel' or type = 'Book' or type = 'Expire' or type = 'Deposit' or type = 'Exchange')";
            Query query = session.createQuery(hql);
            query.setParameter("memberID", memberID);
            list = query.list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<FinanceRecord> findFinanceHostelRecords(String hostelID) {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "SELECT f FROM FinanceRecord f, BookOrder o WHERE " +
                    "o.hostelID = :hostelID and f.orderID = o.id and " +
                    "(f.type = 'Account')";
            Query query = session.createQuery(hql);
            query.setParameter("hostelID", hostelID);
            list = query.list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<FinanceRecord> findFinanceManagerRecords() {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "FROM FinanceRecord WHERE " +
                    "type = 'Book' or type = 'Cancel' or type = 'Account' or type = 'Exchange'";
            Query query = session.createQuery(hql);
            list = query.list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<FinanceRecord> findAllFinanceRecords() {
        return findAll(FinanceRecord.class);
    }
}
