package android.inmobi.com.trendigo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by deepak.jha on 9/12/16.
 */
public class epochConverter {
    public static String epochToDate(Long epochTime){
        Date date = new Date(epochTime);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);
        format.setTimeZone(TimeZone.getTimeZone("India/Mumbai"));
        formatted = format.format(date);
        return formatted;
    }
}
