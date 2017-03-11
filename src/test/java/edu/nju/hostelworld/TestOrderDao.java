package edu.nju.hostelworld;

import edu.nju.hostelworld.dao.OrderDao;
import edu.nju.hostelworld.util.OrderState;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;


/**
 * Created by Sorumi on 17/2/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestOrderDao extends TestCase {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testCount() {
        long count = orderDao.countOrdersByBookedDate("20170208");
        assertEquals(4, count);
    }

    @Test
    public void testFind() {
        long count = orderDao.countMemberOrdersByStateAndDate("0000001", null, "checkOutTime", "2017-03");
        assertEquals(3, count);

//        long count = orderDao.countHostelOrdersByStateAndDate("0000001", null, "bookedTime", "2017-03-01");
//        assertEquals(4, count);

//        long count = orderDao.countMemberOrdersByStateAndDate("0000001", null, "bookedTime", "2017-02");
//        System.out.print(count);
    }

    @Test
    public void testFindByDate() {
        List list = orderDao.findOrderByOrderStateAndMaxDate(OrderState.UnCheckIn, "checkInDate", LocalDate.now().minusDays(1).toString());
        assertEquals(3, list.size());
    }
}
