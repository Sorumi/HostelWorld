package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.BaseDao;
import edu.nju.hostelworld.dao.MemberDao;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.util.ResultMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private BaseDao baseDao;

    public ResultMessage addMember(Member member) {
        try {
            Session session = baseDao.setUpSession();
            session.save(member);
            baseDao.commitAndClose(session);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;

    }

    public ResultMessage updateMember(Member member) {
        try {
            Session session = baseDao.setUpSession();
            session.update(member);
            baseDao.commitAndClose(session);

        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    public long countMember() {
        long count = 0;
        try {
            Session session = baseDao.setUpSession();
            String hql = "select count(*) from Member";
            count = (Long) session.createQuery(hql).uniqueResult();
            baseDao.commitAndClose(session);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Member findMemberByID(String ID) {
        Member member = null;
        try {
            Session session = baseDao.setUpSession();
            Object obj = session.get(Member.class, ID);
//            List list = hibernateTemplateMysql.find("from Member where id = ?", ID);
//            member = list.size() == 0 ? null : (Member) list.get(0);
            member = (Member) obj;
            baseDao.commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return member;
    }

    public Member findMemberByUsername(String username) {
        Member member = null;
        try {
//            List list = hibernateTemplateMysql.find("from Member where username = ?", username);
            Session session = baseDao.setUpSession();
            String hql = "select count(*) from Member";
            List list = session.createQuery(hql).list();
            member = list.size() == 0 ? null : (Member) list.get(0);
            baseDao.commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return member;
    }

    public List<Member> findAllMembers() {
        List list = null;
        try {
            Session session = baseDao.setUpSession();
            String hql = "from Member";
            list = session.createQuery(hql).list();
//            list = hibernateTemplateMysql.find("from Member");
            baseDao.commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
