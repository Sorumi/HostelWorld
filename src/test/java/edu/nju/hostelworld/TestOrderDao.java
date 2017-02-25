package edu.nju.hostelworld;

import edu.nju.hostelworld.dao.OrderDao;
import edu.nju.hostelworld.util.OrderState;
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
        List list = orderDao.findOrderByOrderState(OrderState.Cancelled);
        assertEquals(2, list.size());
    }
}
