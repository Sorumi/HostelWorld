package edu.nju.hostelworld;

import edu.nju.hostelworld.bean.StatisticOrderBean;
import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.service.OrderService;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

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
    public void testExpire() {
        ResultMessage resultMessage = orderService.expireOrder("201702200000");
        assertEquals(ResultMessage.SUCCESS, resultMessage);
    }

    @Test
    public void testFindOrderByID() {
//        List<Integer> counts = orderService.countHostelOrdersByStateAndMonth("0000001", OrderState.CheckIn, "2017-02");
//        assertEquals(28, counts.size());

        List<Integer> yearCounts = orderService.countMemberOrdersByStateAndYear("0000001", OrderState.CheckIn, "2017");
        assertEquals(12, yearCounts.size());
        for (Integer i : yearCounts) {
            if (i != null) {
                System.out.print(i + " ");
            }
        }
    }

    @Test
    public void testHostelStatistic() {
        String hostelID = "0000001";
        String month = "2017-02";
        List<Integer> booked = orderService.countHostelOrdersByStateAndMonth(hostelID, null, month);
        List<Integer> checkIn = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.CheckIn, month);
        List<Integer> checkOut = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.CheckOut, month);
        List<Integer> cancelled = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.Cancelled, month);
        List<Integer> expired = orderService.countHostelOrdersByStateAndMonth(hostelID, OrderState.Expired, month);
        StatisticOrderBean statisticOrderBean = new StatisticOrderBean();
        statisticOrderBean.setBooked(booked);
        statisticOrderBean.setCheckIn(checkIn);
        statisticOrderBean.setCheckOut(checkOut);
        statisticOrderBean.setCancelled(cancelled);
        statisticOrderBean.setExpired(expired);
    }

    @Test
    public void testMemberStatistic() {
        String memberID = "0000001";
        String year = "2017";

        LocalDate date = LocalDate.parse(year + "-01-01");

        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        long time = date.atStartOfDay(zoneId).toEpochSecond() * 1000;

        System.out.print(date + " " + time);

        List<Integer> booked = orderService.countMemberOrdersByStateAndYear(memberID, null, year);
        List<Integer> checkIn = orderService.countMemberOrdersByStateAndYear(memberID, OrderState.CheckIn, year);
        List<Integer> checkOut = orderService.countMemberOrdersByStateAndYear(memberID, OrderState.CheckOut, year);
        List<Integer> cancelled = orderService.countMemberOrdersByStateAndYear(memberID, OrderState.Cancelled, year);
        List<Integer> expired = orderService.countMemberOrdersByStateAndYear(memberID, OrderState.Expired, year);

    }

}

