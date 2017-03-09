package edu.nju.hostelworld;

import edu.nju.hostelworld.model.Level;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.MemberService;
import edu.nju.hostelworld.util.ResultMessage;
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
    public void testUpdate() {
        Member member = memberService.findMemberByID("0000000");
//        member.setName("00");
        member.setAccount("123");

        memberService.updateMember(member);
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

    @Test
    public void testLevel() {
        Level level = memberService.findLevelByMemberID("0000001");
        System.out.println(level.getID());
    }

    @Test
    public void testPause() {
        ResultMessage resultMessage = memberService.pauseMembers();
        resultMessage = memberService.stopMembers();
    }

}
