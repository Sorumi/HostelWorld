package edu.nju.hostelworld;

import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.service.OrderService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Sorumi on 17/2/7.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestOrderService extends TestCase {

    @Autowired
    private OrderService orderService;

    @Test
    public void testAdd() {
        BookOrder bookOrder = new BookOrder();
        bookOrder.setMemberID("0000001");
        bookOrder.setHostelID("0000001");
//        ResultMessage resultMessage = orderService.addOrder(bookOrder);
//        assertEquals(ResultMessage.SUCCESS, resultMessage);
    }

    @Test
    public void testFindOrderByID() {
    }

    @Test
    public void testFindAllMembers() {
    }

}

