package edu.nju.hostelworld;

import edu.nju.hostelworld.dao.MemberDao;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.util.ResultMessage;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * Created by Sorumi on 17/2/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestMemberDao extends TestCase {

    @Autowired
    private MemberDao memberDao;

    @Test
    public void testAdd() {
        Member member = new Member();
        member.setID("1234560");
        member.setUsername("1234005");
        member.setPassword("1243");
        member.setName("宝宝");
        ResultMessage rm = memberDao.addMember(member);
        assertEquals(ResultMessage.SUCCESS, rm);
    }

    @Test
    public void testFind() {
        Member member = memberDao.findMemberByID("0000001");
        System.out.println(member.getName());
        assertNotNull(member);
    }

    @Test
    public void testCount() {
        long count = memberDao.countMembers();
        assertEquals(5, count);
    }

    @Test
    public void testUpdate() {
        Member member = memberDao.findMemberByID("0000001");
        member.setName("234444");
        ResultMessage rm = memberDao.updateMember(member);
        assertEquals(ResultMessage.SUCCESS, rm);
    }
}
