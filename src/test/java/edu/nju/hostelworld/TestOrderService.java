package edu.nju.hostelworld;

import edu.nju.hostelworld.bean.StatisticOrderBean;
import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.service.OrderService;
import edu.nju.hostelworld.util.OrderState;
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
        int[] counts = orderService.countHostelOrdersByStateAndMonth("0000001", OrderState.CheckIn, "2017-02");
        assertEquals(28, counts.length);
        for (int i : counts) {
            System.out.print(i + " ");
        }
    }

    @Test
    public void testFindAllMembers() {
        String hostelID = "0000001";
        String month = "2017-02";
        int[] booked = orderService.countHostelOrdersByStateAndMonth(hostelID, null, month);
        int[] checkIn = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.CheckIn, month);
        int[] checkOut = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.CheckOut, month);
        int[] cancelled = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.Cancelled, month);
        int[] expired = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.Expired, month);
        StatisticOrderBean statisticOrderBean = new StatisticOrderBean();
        statisticOrderBean.setBooked(booked);
        statisticOrderBean.setCheckIn(checkIn);
        statisticOrderBean.setCheckOut(checkOut);
        statisticOrderBean.setCancelled(cancelled);
        statisticOrderBean.setExpired(expired);
    }

}

