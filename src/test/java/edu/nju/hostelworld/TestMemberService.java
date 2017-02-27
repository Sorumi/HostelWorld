package edu.nju.hostelworld;

import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.MemberService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Sorumi on 17/2/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestMemberService extends TestCase {

    @Autowired
    private MemberService memberService;

    @Test
    public void testAdd() {
        Member member = new Member();
        member.setID("0000001");
        member.setUsername("1230");
        member.setPassword("123");
        member.setName("名字");
        memberService.addMember(member);
    }

    @Test
    public void testFindMemberByID() {
        Member member = memberService.findMemberByID("0000001");
        assertNotNull(member);
    }

    @Test
    public void testFindAllMembers() {
        List members = memberService.findAllMembers();
        assertEquals(members.size(), 2);
    }

}
