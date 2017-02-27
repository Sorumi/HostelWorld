package edu.nju.hostelworld.util;

import java.util.regex.Pattern;

/**
 * Created by Sorumi on 17/2/27.
 */
public class PatternUtil {

    public static boolean isPrice(String string) {
        Pattern pricePattern = Pattern.compile("^[0-9]+(.[0-9]?[0-9]?)?$");
        return pricePattern.matcher(string).matches();
    }

}
