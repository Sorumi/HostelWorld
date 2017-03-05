package edu.nju.hostelworld.task;

import edu.nju.hostelworld.service.MemberService;
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
public class MemberTask {

    @Autowired
    private MemberService memberService;

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 60 * 10 * 1000)
    public void pauseMember() {
        System.out.println(LocalDateTime.now() + " pause members");
        memberService.pauseMembers();
        memberService.stopMembers();
    }
}
