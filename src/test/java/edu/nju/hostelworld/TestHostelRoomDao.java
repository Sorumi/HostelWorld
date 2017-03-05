package edu.nju.hostelworld;


import edu.nju.hostelworld.dao.HostelRoomDao;
import edu.nju.hostelworld.model.RoomStock;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by Sorumi on 17/2/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestHostelRoomDao extends TestCase {

    @Autowired
    private HostelRoomDao hostelRoomDao;

    @Test
    public void testCount() {
        long count = hostelRoomDao.countHostelRoomsByHostelID("0000001");
        assertEquals(4, count);
    }

    @Test
    public void testFindHostelRoomByDate() {
        List list = hostelRoomDao.findHostelRoomsByHostelIDAndDate("0000001", "2017-02-10", "2017-02-10");
        assertEquals(3, list.size());
    }

    @Test
    public void testFindRoomStockByDate() {
        RoomStock roomStock = hostelRoomDao.findRoomStockByHostelRoomIDAndDate("0000001000", "2017-02-10");
        assertNotNull(roomStock);
    }

    @Test
    public void testMinQuantityOfRoomStock() {
        long quantity = hostelRoomDao.minQuantityOfRoomStockByHostelRoomIDAndDate("0000001000", "2017-02-07", "2017-02-12");
        assertEquals(9, quantity);
    }
}
