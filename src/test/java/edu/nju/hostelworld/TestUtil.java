package edu.nju.hostelworld;

import junit.framework.TestCase;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by Sorumi on 17/3/6.
 */
public class TestUtil extends TestCase {

    @Test
    public void testDate() {
        LocalDate date = LocalDate.parse("2017-02");


//        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
//        long time = date.atStartOfDay(zoneId).toEpochSecond();

        System.out.print(date);
    }
}
