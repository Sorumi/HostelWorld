package edu.nju.hostelworld;

import edu.nju.hostelworld.dao.HostelDao;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.util.HostelState;
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
public class TestHostelDao extends TestCase {

    @Autowired
    private HostelDao hostelDao;

    @Test
    public void testAdd() {
        Hostel hostel = new Hostel();
        hostel.setID("0000002");
        hostel.setUsername("hostel2");
        hostel.setPassword("123");
        hostel.setState(HostelState.Opening);
        hostelDao.addHostel(hostel);
    }

    @Test
    public void testUpdate() {
        Hostel hostel = hostelDao.findHostelByID("0000001");
        hostel.setPassword("123");
        hostel.setState(HostelState.Opening);
        ResultMessage resultMessage = hostelDao.updateHostel(hostel);
        assertEquals(ResultMessage.SUCCESS, resultMessage);
    }

    @Test
    public void testFindByID() {
        Hostel hostel = hostelDao.findHostelByID("0000001");
        assertNotNull(hostel);
    }

    @Test
    public void testFindByState() {
        List list = hostelDao.findHostelsByState(HostelState.Unopened);
        assertEquals(1, list.size());
    }

    @Test
    public void testFindByKeyword() {
        List list = hostelDao.findHostelByKeyword("address", "南京西路");
        assertEquals(1, list.size());
    }

    @Test
    public void testCountHostels() {
        long count = hostelDao.countHostels();
        assertEquals(2, count);
    }

}
