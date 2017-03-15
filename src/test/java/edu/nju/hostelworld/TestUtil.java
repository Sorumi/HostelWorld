package edu.nju.hostelworld;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Created by Sorumi on 17/3/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestUtil extends TestCase {

    @Test
    public void testDate() {
        LocalDate date = LocalDate.parse("2017-02-01");


        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        long time = date.atStartOfDay(zoneId).toEpochSecond();

        System.out.print(time);

//        long days = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusDays(1));
//
//        System.out.print(days);
    }
}
