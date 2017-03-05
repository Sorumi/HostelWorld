package edu.nju.hostelworld.task;

import edu.nju.hostelworld.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by Sorumi on 17/3/7.
 */

@Component
@Lazy(false)
public class OrderTask {

    @Autowired
    OrderService orderService;

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 60 * 10 * 1000)
    public void expiredOrder() {
        System.out.println(LocalDateTime.now() + " expire orders");
        orderService.expireOrders();
    }

}
