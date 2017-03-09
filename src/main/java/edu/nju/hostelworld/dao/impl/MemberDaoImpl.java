package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.BaseDao;
import edu.nju.hostelworld.dao.MemberDao;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.util.MemberState;
import edu.nju.hostelworld.util.ResultMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by Sorumi on 17/2/2.
 */

@Repository
public class MemberDaoImpl extends BaseDaoImpl implements MemberDao {

    public ResultMessage addMember(Member member) {
        try {
            Session session = setUpSession();
            session.save(member);
            commitAndClose(session);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;

    }

    public ResultMessage updateMember(Member member) {
        try {
            Session session = setUpSession();
            session.update(member);
            commitAndClose(session);

        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    public long countMember() {
        long count = 0;
        try {
            Session session = setUpSession();
            String hql = "select count(*) from Member";
            count = (Long) session.createQuery(hql).uniqueResult();
            commitAndClose(session);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Member findMemberByID(String ID) {
        Member member = null;
        try {
            Session session = setUpSession();
            Object obj = session.get(Member.class, ID);
            member = (Member) obj;
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return member;
    }

    public Member findMemberByUsername(String username) {
        Member member = null;
        try {
            Session session = setUpSession();
            String hql = "from Member where username = :username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            List list = query.list();
            member = list.size() == 0 ? null : (Member) list.get(0);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return member;
    }

    public List<Member> findAllMembers() {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from Member";
            list = session.createQuery(hql).list();
//            list = hibernateTemplateMysql.find("from Member");
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Member> findMembersByStateAndMaxDate(MemberState memberState, String dateField, String date) {
        List list = null;
        try {
            Session session = setUpSession();
            String hql = "from Member where state = :state and " + dateField + " < CONCAT(:date)";
            Query query = session.createQuery(hql);
            query.setParameter("state", memberState);
            query.setParameter("date", date);
            list = query.list();
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
