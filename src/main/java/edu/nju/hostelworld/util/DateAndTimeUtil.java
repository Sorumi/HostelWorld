package edu.nju.hostelworld.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Sorumi on 17/2/5.
 */
public class DateAndTimeUtil {

    public static String dateStringWithHyphen(LocalDate date) {
        return date.toString();
    }

    public static String timeStringWithHyphen(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    public static String dateStringWithOutSeparator(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return  date.format(formatter);
    }

    public static String monthStringWithHyphen(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return  date.format(formatter);
    }

}
